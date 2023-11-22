package ch.zuehlke.f1telemetrytransformer.kafka.processor;

import ch.zuehlke.f1telemetrytransformer.kafka.model.JsonSerde;
import ch.zuehlke.f1telemetrytransformer.kafka.model.LapUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.kafka.model.SectorUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.LapTimeService;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LaptimeUpdateProcessor {
    private final Serde<String> STRING_SERDE = Serdes.String();
    private final Serde<LapUpdateMessage> LAPUPDATE_SERDE = new JsonSerde<>(LapUpdateMessage.class);
    private final Serde<SectorUpdateMessage> SECTORUPDATE_SERDE = new JsonSerde<>(SectorUpdateMessage.class);

    @Value("${kafka.topics.input.lapupdate}")
    private String lapUpdateTopic;
    @Value("${kafka.topics.input.sectorupdate}")
    private String sectorTimeTopic;

    @Value("${kafka.topics.output.laptimeupdate}")
    private String lapTimeUpdateTopic;
    private final LapTimeService lapTimeService;

    public LaptimeUpdateProcessor(LapTimeService lapTimeService) {
        this.lapTimeService = lapTimeService;
    }

    @Autowired
    void buildLapUpdatePipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream(lapUpdateTopic, Consumed.with(STRING_SERDE, LAPUPDATE_SERDE))
                .peek((k, v) -> System.out.println("Processing LapUpdate Topic: " + v))
                .mapValues(lapTimeService::handleLapTimeMessageEvent)
                .to(lapTimeUpdateTopic);
    }

    @Autowired
    void buildSectorUpdatePipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream(sectorTimeTopic, Consumed.with(STRING_SERDE, SECTORUPDATE_SERDE))
                .peek((k, v) -> System.out.println("Processing SectorUpdate Topic: " + v))
                .mapValues(lapTimeService::handleSectorUpdateMessageEvent)
                .to(lapTimeUpdateTopic);
    }
}

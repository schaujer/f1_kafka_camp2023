package ch.zuehlke.f1telemetrytransformer.kafka.processor;

import ch.zuehlke.f1telemetrytransformer.kafka.JsonSerde;
import ch.zuehlke.f1telemetrytransformer.kafka.model.LapUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.kafka.model.SectorUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.LapTimeService;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LaptimeUpdateProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LaptimeUpdateProcessor.class);
    private final Serde<String> STRING_SERDE = Serdes.String();
    private final Serde<LapUpdateMessage> LAPUPDATE_SERDE = new JsonSerde<>(LapUpdateMessage.class);
    private final Serde<SectorUpdateMessage> SECTORUPDATE_SERDE = new JsonSerde<>(SectorUpdateMessage.class);


    private final String lapUpdateTopic;
    private final String sectorUpdateTopic;
    private final String lapTimeUpdateTopic;
    private final LapTimeService lapTimeService;

    public LaptimeUpdateProcessor(
            LapTimeService lapTimeService,
            @Value("${kafka.topics.input.lapupdate}") String lapUpdateTopic,
            @Value("${kafka.topics.input.sectorupdate}") String sectorUpdateTopic,
            @Value("${kafka.topics.output.laptimeupdate}") String lapTimeUpdateTopic) {
        this.lapTimeService = lapTimeService;
        this.lapUpdateTopic = lapUpdateTopic;
        this.sectorUpdateTopic = sectorUpdateTopic;
        this.lapTimeUpdateTopic = lapTimeUpdateTopic;
    }

    @Autowired
    void buildLapUpdatePipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream(lapUpdateTopic, Consumed.with(STRING_SERDE, LAPUPDATE_SERDE))
                .peek((k, v) -> LOGGER.debug("Processing LapUpdate Topic: " + v))
                .mapValues(lapTimeService::handleLapTimeMessageEvent)
                .to(lapTimeUpdateTopic);
    }

    @Autowired
    void buildSectorUpdatePipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream(sectorUpdateTopic, Consumed.with(STRING_SERDE, SECTORUPDATE_SERDE))
                .peek((k, v) -> LOGGER.debug("Processing SectorUpdate Topic: " + v))
                .mapValues(lapTimeService::handleSectorUpdateMessageEvent)
                .to(lapTimeUpdateTopic);
    }
}

package ch.zuehlke.f1telemetrytransformer.kafka.processor;

import ch.zuehlke.f1telemetrytransformer.kafka.model.LapTimeMessage;
import ch.zuehlke.f1telemetrytransformer.service.LapTimeService;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

@Component
public class LaptimeUpdateProcessor {
    private final Serde<String> STRING_SERDE = Serdes.String();
    private final Serde<LapTimeMessage> LAPTIME_SERDE = Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(LapTimeMessage.class));

    @Value("${kafka.topics.input.laptime}")
    private String lapTimeTopic;
    @Value("${kafka.topics.output.laptimeupdate}")
    private String lapTimeUpdateTopic;
    private final LapTimeService lapTimeService;

    public LaptimeUpdateProcessor(LapTimeService lapTimeService) {
        this.lapTimeService = lapTimeService;
    }

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream(lapTimeTopic, Consumed.with(STRING_SERDE, LAPTIME_SERDE))
                .peek((k, v) -> System.out.println("Processing lapTimeTopic: " + v))
                .mapValues(lapTimeService::handleLapTimeMessageEvent)
                .to(lapTimeUpdateTopic);
    }
}

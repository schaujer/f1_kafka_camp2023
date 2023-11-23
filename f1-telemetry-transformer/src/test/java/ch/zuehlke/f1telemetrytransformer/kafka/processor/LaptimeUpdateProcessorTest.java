package ch.zuehlke.f1telemetrytransformer.kafka.processor;

import ch.zuehlke.f1telemetrytransformer.kafka.JsonSerde;
import ch.zuehlke.f1telemetrytransformer.kafka.model.SectorUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.LapTimeService;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LaptimeUpdateProcessorTest {
    private LapTimeService lapTimeService;
    private LaptimeUpdateProcessor testee;
    private static final String SECTORUPDATE_TOPIC = "SectorUpdate";
    private static final String LAPUPDATE_TOPIC = "LapUpdate";
    private static final String LAPTIMEUPDATE_TOPIC = "LapTimeUpdate";

    @BeforeEach
    void setup() {
        lapTimeService = mock(LapTimeService.class);
        testee = new LaptimeUpdateProcessor(lapTimeService, LAPUPDATE_TOPIC, SECTORUPDATE_TOPIC, LAPTIMEUPDATE_TOPIC);
    }

    @Test
    void testSectorUpdatePipeline() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        testee.buildSectorUpdatePipeline(streamsBuilder);
        Topology topology = streamsBuilder.build();

        SectorUpdateMessage updateMessage = new SectorUpdateMessage();
        updateMessage.setDriver("VER");
        updateMessage.setSector1Time(123.0);

        LapTimeUpdate lapTimeUpdate = new LapTimeUpdate();
        lapTimeUpdate.setDriver("VER");
        lapTimeUpdate.setSector1Time(123.0);

        when(lapTimeService.handleSectorUpdateMessageEvent(any(SectorUpdateMessage.class)))
                .thenReturn(lapTimeUpdate);

        try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, getTestKafkaProperties())) {
            TestInputTopic<String, SectorUpdateMessage> inputTopic = topologyTestDriver
                    .createInputTopic(SECTORUPDATE_TOPIC, new StringSerializer(), new JsonSerializer<>());

            TestOutputTopic<String, LapTimeUpdate> outputTopic = topologyTestDriver
                    .createOutputTopic(LAPTIMEUPDATE_TOPIC, new StringDeserializer(), new JsonSerde<>(LapTimeUpdate.class).deserializer());

            inputTopic.pipeInput("test", updateMessage);

            assertThat(outputTopic.readKeyValue()).isEqualTo(KeyValue.pair("test", lapTimeUpdate));
        }
    }


    private static Properties getTestKafkaProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerde.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }
}
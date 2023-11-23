package ch.zuehlke.f1telemetrytransformer.kafka.processor;

import ch.zuehlke.f1telemetrytransformer.kafka.JsonSerde;
import ch.zuehlke.f1telemetrytransformer.kafka.KafkaTestUtil;
import ch.zuehlke.f1telemetrytransformer.kafka.model.LapUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.kafka.model.SectorUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.LapTimeService;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonSerializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LaptimeUpdateProcessorTest {
    private LapTimeService lapTimeService;
    private LaptimeUpdateProcessor testee;

    @BeforeEach
    void setup() {
        lapTimeService = mock(LapTimeService.class);
        testee = new LaptimeUpdateProcessor(lapTimeService, KafkaTestUtil.LAPUPDATE_TOPIC,
                KafkaTestUtil.SECTORUPDATE_TOPIC, KafkaTestUtil.LAPTIMEUPDATE_TOPIC);
    }

    @Test
    void testLapUpdatePipeline() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        testee.buildLapUpdatePipeline(streamsBuilder);
        Topology topology = streamsBuilder.build();

        LapUpdateMessage updateMessage = new LapUpdateMessage();
        updateMessage.setDriver("ALO");
        updateMessage.setLapTime(234.0);

        LapTimeUpdate lapTimeUpdate = new LapTimeUpdate();
        lapTimeUpdate.setDriver("ALO");
        lapTimeUpdate.setLapTime(234.0);

        when(lapTimeService.handleLapTimeMessageEvent(updateMessage)).thenReturn(lapTimeUpdate);

        try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, KafkaTestUtil.getTestKafkaProperties())) {
            TestInputTopic<String, LapUpdateMessage> inputTopic = topologyTestDriver
                    .createInputTopic(KafkaTestUtil.LAPUPDATE_TOPIC, new StringSerializer(), new JsonSerializer<>());

            TestOutputTopic<String, LapTimeUpdate> outputTopic = topologyTestDriver
                    .createOutputTopic(KafkaTestUtil.LAPTIMEUPDATE_TOPIC, new StringDeserializer(),
                            new JsonSerde<>(LapTimeUpdate.class).deserializer());

            inputTopic.pipeInput("test", updateMessage);

            assertThat(outputTopic.readKeyValue()).isEqualTo(KeyValue.pair("test", lapTimeUpdate));
        }
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

        when(lapTimeService.handleSectorUpdateMessageEvent(updateMessage)).thenReturn(lapTimeUpdate);

        try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, KafkaTestUtil.getTestKafkaProperties())) {
            TestInputTopic<String, SectorUpdateMessage> inputTopic = topologyTestDriver
                    .createInputTopic(KafkaTestUtil.SECTORUPDATE_TOPIC, new StringSerializer(), new JsonSerializer<>());

            TestOutputTopic<String, LapTimeUpdate> outputTopic = topologyTestDriver
                    .createOutputTopic(KafkaTestUtil.LAPTIMEUPDATE_TOPIC, new StringDeserializer(),
                            new JsonSerde<>(LapTimeUpdate.class).deserializer());

            inputTopic.pipeInput("test", updateMessage);

            assertThat(outputTopic.readKeyValue()).isEqualTo(KeyValue.pair("test", lapTimeUpdate));
        }
    }
}
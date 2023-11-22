package ch.zuehlke.f1telemetrytransformer.config;

import ch.zuehlke.f1telemetrytransformer.kafka.model.SectorUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;

@EnableKafka
@EnableKafkaStreams
@Configuration
public class KafkaConsumerConfig {
    private final String bootstrapServer;

    public KafkaConsumerConfig(@Value("${spring.cloud.stream.kafka.binder.brokers}") String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        return new ConcurrentKafkaListenerContainerFactory<>();
    }

    //@Bean
    //public ConcurrentKafkaListenerContainerFactory<String, LapTimeMessage> lapTimeListenerContainerFactory() {
    //    ConcurrentKafkaListenerContainerFactory<String, LapTimeMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
    //    factory.setConsumerFactory(consumerFactory(LapTimeMessage.class));
    //    return factory;
    //}

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LapTimeUpdate> lapTimeUpdateListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LapTimeUpdate> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(LapTimeUpdate.class));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SectorUpdateMessage> sectorUpdateListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SectorUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(SectorUpdateMessage.class));
        return factory;
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, "streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);

        return new KafkaStreamsConfiguration(props);
    }

    private <T> ConsumerFactory<String, T> consumerFactory(Class<T> messageClass) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id-" + messageClass.getSimpleName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        JsonDeserializer<T> jsonDeserializer = new JsonDeserializer<>(messageClass, false);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }
}
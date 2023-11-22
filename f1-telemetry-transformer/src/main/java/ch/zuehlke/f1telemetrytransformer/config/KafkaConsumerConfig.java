package ch.zuehlke.f1telemetrytransformer.config;

import ch.zuehlke.f1telemetrytransformer.model.LapTimeMessage;
import ch.zuehlke.f1telemetrytransformer.model.TemperatureMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
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

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TemperatureMessage> temperatureListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TemperatureMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(TemperatureMessage.class));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LapTimeMessage> lapTimeListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LapTimeMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(LapTimeMessage.class));
        return factory;
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
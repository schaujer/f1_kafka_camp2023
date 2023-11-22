package ch.zuehlke.f1telemetrytransformer.kafka;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class JsonSerde<T> implements Serde<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> targetType;

    public JsonSerde(Class<T> targetType) {
        this.targetType = targetType;

        objectMapper = new ObjectMapper()
                .enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());
    }

    @Override
    public Serializer<T> serializer() {
        return new JsonSerializer<>(objectMapper);
    }

    @Override
    public Deserializer<T> deserializer() {
        return new JsonDeserializer<>(targetType, objectMapper);
    }
}

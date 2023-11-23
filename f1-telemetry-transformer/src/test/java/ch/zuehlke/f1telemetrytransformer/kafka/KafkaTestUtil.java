package ch.zuehlke.f1telemetrytransformer.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Properties;

public class KafkaTestUtil {
    public static final String SECTORUPDATE_TOPIC = "SectorUpdate";
    public static final String LAPUPDATE_TOPIC = "LapUpdate";
    public static final String LAPTIMEUPDATE_TOPIC = "LapTimeUpdate";

    public static Properties getTestKafkaProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }
}

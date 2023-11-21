package ch.zuehlke.f1telemetrytransformer.enhancer;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.function.Function;

//@Configuration
// Currently unused
public class TestTemperatureEnhancer {

    @Bean
    public Function<KStream<String, Long>, KStream<String, String>> enhancer() {
        return input -> input
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10)))
                .aggregate(() -> 0l,
                        (key, value, aggregate) -> aggregate + value,
                        Materialized.with(Serdes.String(), Serdes.Long()))

                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map((w, v) ->  new KeyValue<>(w.key(), "Avg -- " + (v / 10)));
    }

}

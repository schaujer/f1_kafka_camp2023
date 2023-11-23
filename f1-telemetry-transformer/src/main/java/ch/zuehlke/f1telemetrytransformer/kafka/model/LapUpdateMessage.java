package ch.zuehlke.f1telemetrytransformer.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LapUpdateMessage {
    @JsonProperty("DataType")
    private String dataType;
    @JsonProperty("LapNumber")
    private int lapNumber;
    @JsonProperty("LapTime")
    private double lapTime;
    @JsonProperty("Driver")
    private String driver;
    @JsonProperty("Position")
    private int position;
    @JsonProperty("Timestamp")
    private Double timestamp;
}

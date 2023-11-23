package ch.zuehlke.f1telemetrytransformer.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SectorUpdateMessage {
    @JsonProperty("DataType")
    private String dataType;
    @JsonProperty("LapNumber")
    private int lapNumber;
    @JsonProperty("Driver")
    private String driver;
    @JsonProperty("Sector1Time")
    private Double sector1Time;
    @JsonProperty("Sector2Time")
    private Double sector2Time;
    @JsonProperty("Sector3Time")
    private Double sector3Time;
    @JsonProperty("Timestamp")
    private double timestamp;
}

package ch.zuehlke.f1telemetrytransformer.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SectorUpdateMessage {
    @JsonProperty("DataType")
    private String dataType;
    @JsonProperty("LapNumber")
    private int lapNumber;
    @JsonProperty("Driver")
    private String driver;
    @JsonProperty("Sector1Time")
    private double sector1Time;
    @JsonProperty("Sector2Time")
    private double sector2Time;
    @JsonProperty("Sector3Time")
    private double sector3Time;
    @JsonProperty("Timestamp")
    private double timestamp;
}

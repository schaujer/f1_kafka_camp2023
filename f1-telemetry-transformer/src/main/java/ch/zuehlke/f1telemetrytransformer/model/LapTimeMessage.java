package ch.zuehlke.f1telemetrytransformer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LapTimeMessage {
    @JsonProperty("DataType")
    private String dataType;
    @JsonProperty("LapNumber")
    private int lapNumber;
    @JsonProperty("LapStartTime")
    private double lapStartTime;
    @JsonProperty("LapDuration")
    private double lapDuration;
    @JsonProperty("Time")
    private double time;
    @JsonProperty("Driver")
    private String driver;
    @JsonProperty("Position")
    private int position;
    @JsonProperty("Timestamp")
    private double timestamp;
}

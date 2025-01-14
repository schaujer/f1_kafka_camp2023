package ch.zuehlke.f1telemetrytransformer.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TelemetryUpdateMessage {
    @JsonProperty("Driver")
    private String driver;
    @JsonProperty("Speed")
    private float speed;
    @JsonProperty("RPM")
    private int rpm;
    @JsonProperty("nGear")
    private int gear;
    @JsonProperty("Throttle")
    private float throttle;
    @JsonProperty("Brake")
    private boolean brake;
    @JsonProperty("DRS")
    private int drs;
    @JsonProperty("X")
    private float x;
    @JsonProperty("Y")
    private float y;
    @JsonProperty("Z")
    private float z;
}

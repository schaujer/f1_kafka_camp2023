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
public class TelemetryUpdateMessage {
    @JsonProperty("Driver")
    private String driver;
    private float speed;
    private int rpm;
    private int gear;
    private float throttle;
    private boolean brake;
    private int drs;
}

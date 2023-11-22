package ch.zuehlke.f1telemetrytransformer.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class TelemetryUpdate {
    private float speed;
    private int rpm;
    private int gear;
    private float throttle;
    private boolean brake;
    private int drs;
}

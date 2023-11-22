package ch.zuehlke.f1telemetrytransformer.service.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LapTimeUpdate {
    private int lapNumber;
    private double lapTime;
    private String driver;
    private int position;
    private double sector1Time;
    private double sector2Time;
    private double sector3Time;
    private double timestamp;
}

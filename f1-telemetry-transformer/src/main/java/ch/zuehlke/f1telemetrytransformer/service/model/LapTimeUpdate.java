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
    private Double lapTime;
    private String driver;
    private int position;
    private Double sector1Time;
    private Double sector2Time;
    private Double sector3Time;
    private Double timestamp;
}

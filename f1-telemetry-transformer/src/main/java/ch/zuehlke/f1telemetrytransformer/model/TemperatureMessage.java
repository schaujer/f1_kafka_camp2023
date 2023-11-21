package ch.zuehlke.f1telemetrytransformer.model;

public class TemperatureMessage {
    private long temperature;

    public TemperatureMessage() {
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }
}

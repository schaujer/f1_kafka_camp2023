package ch.zuehlke.f1telemetrytransformer.model;

public class TemperatureMessage {
    private double temperature;

    public TemperatureMessage() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TemperatureMessage{" +
                "temperature=" + temperature +
                '}';
    }
}

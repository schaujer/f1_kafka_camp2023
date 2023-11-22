package ch.zuehlke.f1telemetrytransformer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public LapTimeMessage() {
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getLapNumber() {
        return lapNumber;
    }

    public void setLapNumber(int lapNumber) {
        this.lapNumber = lapNumber;
    }

    public double getLapStartTime() {
        return lapStartTime;
    }

    public void setLapStartTime(double lapStartTime) {
        this.lapStartTime = lapStartTime;
    }

    public double getLapDuration() {
        return lapDuration;
    }

    public void setLapDuration(double lapDuration) {
        this.lapDuration = lapDuration;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LapTimeMessage{" +
                "dataType='" + dataType + '\'' +
                ", lapNumber=" + lapNumber +
                ", lapStartTime=" + lapStartTime +
                ", lapDuration=" + lapDuration +
                ", time=" + time +
                ", driver='" + driver + '\'' +
                ", position=" + position +
                ", timestamp=" + timestamp +
                '}';
    }
}

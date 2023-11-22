package ch.zuehlke.f1telemetrytransformer.service;

import ch.zuehlke.f1telemetrytransformer.kafka.model.LapTimeMessage;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LapTimeService {
    private final HashMap<String, LapTimeUpdate> lapTimeCache = new HashMap<>();

    public LapTimeUpdate handleLapTimeMessageEvent(LapTimeMessage lapTimeMessage) {
        LapTimeUpdate lapTimeUpdate = new LapTimeUpdate();
        lapTimeUpdate.setLapNumber(lapTimeMessage.getLapNumber());
        lapTimeUpdate.setLapTime(lapTimeMessage.getLapTime());
        lapTimeUpdate.setDriver(lapTimeMessage.getDriver());
        lapTimeUpdate.setPosition(lapTimeMessage.getPosition());
        lapTimeUpdate.setTimestamp(lapTimeMessage.getTimestamp());

        lapTimeCache.put(lapTimeMessage.getDriver(), lapTimeUpdate);
        return lapTimeUpdate;
    }
}

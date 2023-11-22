package ch.zuehlke.f1telemetrytransformer.service;

import ch.zuehlke.f1telemetrytransformer.kafka.model.LapUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.kafka.model.SectorUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LapTimeService {
    private final HashMap<String, LapTimeUpdate> lapTimeCache = new HashMap<>();

    public LapTimeUpdate handleLapTimeMessageEvent(LapUpdateMessage lapUpdateMessage) {
        LapTimeUpdate lapTimeUpdate = new LapTimeUpdate();
        lapTimeUpdate.setLapNumber(lapUpdateMessage.getLapNumber());
        lapTimeUpdate.setLapTime(lapUpdateMessage.getLapTime());
        lapTimeUpdate.setDriver(lapUpdateMessage.getDriver());
        lapTimeUpdate.setPosition(lapUpdateMessage.getPosition());
        lapTimeUpdate.setTimestamp(lapUpdateMessage.getTimestamp());

        lapTimeCache.put(lapUpdateMessage.getDriver(), lapTimeUpdate);
        return lapTimeUpdate;
    }

    public LapTimeUpdate handleSectorUpdateMessageEvent(SectorUpdateMessage sectorUpdateMessage) {
        LapTimeUpdate lapTimeUpdate;
        if (lapTimeCache.containsKey(sectorUpdateMessage.getDriver())) {
            lapTimeUpdate = lapTimeCache.get(sectorUpdateMessage.getDriver());
        } else {
            lapTimeUpdate = new LapTimeUpdate();
            lapTimeUpdate.setDriver(sectorUpdateMessage.getDriver());
        }

        lapTimeUpdate.setLapNumber(sectorUpdateMessage.getLapNumber());
        lapTimeUpdate.setSector1Time(sectorUpdateMessage.getSector1Time());
        lapTimeUpdate.setSector2Time(sectorUpdateMessage.getSector2Time());
        lapTimeUpdate.setSector3Time(sectorUpdateMessage.getSector3Time());
        lapTimeUpdate.setTimestamp(sectorUpdateMessage.getTimestamp());
        lapTimeCache.put(sectorUpdateMessage.getDriver(), lapTimeUpdate);
        return lapTimeUpdate;
    }
}

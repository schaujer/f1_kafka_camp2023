package ch.zuehlke.f1telemetrytransformer.service;

import ch.zuehlke.f1telemetrytransformer.kafka.model.LapUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.kafka.model.SectorUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LapTimeService {
    private final HashMap<String, LapTimeUpdate> lapTimeCache = new HashMap<>();

    public List<LapTimeUpdate> getCurrentLapTimeUpdates() {
        return lapTimeCache.values().stream().toList();
    }

    public LapTimeUpdate handleLapTimeMessageEvent(LapUpdateMessage lapUpdateMessage) {
        LapTimeUpdate lapTimeUpdate = getLapTimeEntryForDriver(lapUpdateMessage.getDriver());

        lapTimeUpdate.setLapNumber(lapUpdateMessage.getLapNumber() + 1);
        lapTimeUpdate.setLapTime(lapUpdateMessage.getLapTime());
        lapTimeUpdate.setPosition(lapUpdateMessage.getPosition());
        lapTimeUpdate.setTimestamp(lapUpdateMessage.getTimestamp());

        lapTimeCache.put(lapUpdateMessage.getDriver(), lapTimeUpdate);
        return lapTimeUpdate;
    }

    public LapTimeUpdate handleSectorUpdateMessageEvent(SectorUpdateMessage sectorUpdateMessage) {
        LapTimeUpdate lapTimeUpdate = getLapTimeEntryForDriver(sectorUpdateMessage.getDriver());

        lapTimeUpdate.setLapNumber(sectorUpdateMessage.getLapNumber());
        lapTimeUpdate.setSector1Time(sectorUpdateMessage.getSector1Time());
        lapTimeUpdate.setSector2Time(sectorUpdateMessage.getSector2Time());
        lapTimeUpdate.setSector3Time(sectorUpdateMessage.getSector3Time());
        lapTimeUpdate.setTimestamp(sectorUpdateMessage.getTimestamp());

        lapTimeCache.put(sectorUpdateMessage.getDriver(), lapTimeUpdate);
        return lapTimeUpdate;
    }

    private LapTimeUpdate getLapTimeEntryForDriver(String driver) {
        LapTimeUpdate lapTimeUpdate;

        if (lapTimeCache.containsKey(driver)) {
            lapTimeUpdate = lapTimeCache.get(driver);
        } else {
            lapTimeUpdate = new LapTimeUpdate();
            lapTimeUpdate.setDriver(driver);
        }

        return lapTimeUpdate;
    }
}

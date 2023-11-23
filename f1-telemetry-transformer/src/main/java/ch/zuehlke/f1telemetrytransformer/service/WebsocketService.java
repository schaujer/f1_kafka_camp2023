package ch.zuehlke.f1telemetrytransformer.service;

import ch.zuehlke.f1telemetrytransformer.kafka.model.TelemetryUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import ch.zuehlke.f1telemetrytransformer.service.model.TelemetryUpdate;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final static String LAPTIMES_TOPIC = "/topic/laptimes";
    private final static String TELEMETRY_TOPIC = "/topic/telemetry";

    private final SimpMessagingTemplate template;

    public void sendLaptimeUpdate(LapTimeUpdate update) {
        template.convertAndSend(LAPTIMES_TOPIC, update);
    }

    public void sendTelemetryUpdate(TelemetryUpdateMessage update) {
        TelemetryUpdate telemetryUpdate = convertToTelemetryUpdate(update);

        String path = TELEMETRY_TOPIC + "/" + update.getDriver();
        template.convertAndSend(path, telemetryUpdate);
    }

    @NotNull
    private static TelemetryUpdate convertToTelemetryUpdate(TelemetryUpdateMessage update) {
        TelemetryUpdate telemetryUpdate = new TelemetryUpdate();
        telemetryUpdate.setGear(update.getGear());
        telemetryUpdate.setRpm(update.getRpm());
        telemetryUpdate.setDrs(convertToIsDrsEnabled(update.getDrs()));
        telemetryUpdate.setSpeed(update.getSpeed());
        telemetryUpdate.setBrake(update.isBrake());
        telemetryUpdate.setThrottle(update.getThrottle());
        telemetryUpdate.setX(update.getX());
        telemetryUpdate.setY(update.getY());
        telemetryUpdate.setZ(update.getZ());

        return telemetryUpdate;
    }

    private static boolean convertToIsDrsEnabled(int drsValue) {
        if (drsValue == 0) {
            return false;
        } else {
            return (drsValue % 2) == 0;
        }
    }
}

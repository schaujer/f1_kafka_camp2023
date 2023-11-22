package ch.zuehlke.f1telemetrytransformer.kafka.consumer;

import ch.zuehlke.f1telemetrytransformer.kafka.model.TelemetryUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.WebsocketService;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final WebsocketService websocketService;

    @KafkaListener(topics = "${kafka.topics.output.laptimeupdate}", containerFactory = "lapTimeUpdateListenerContainerFactory")
    public void consumeLapTimeMessage(@Payload LapTimeUpdate message) {
        System.out.println("Consuming LapTimeUpdate Topic:" + message);
        websocketService.sendLaptimeUpdate(message);
    }

    @KafkaListener(topics = "${kafka.topics.input.telemetryupdate}", containerFactory = "telemetryUpdateListenerContainerFactory")
    public void consumeTelemetryMessage(@Payload TelemetryUpdateMessage message) {
        System.out.println("Consuming TelemetryUpdate Topic: " + message);
        websocketService.sendTelemetryUpdate(message);
    }
}
package ch.zuehlke.f1telemetrytransformer.kafka.consumer;

import ch.zuehlke.f1telemetrytransformer.kafka.model.TelemetryUpdateMessage;
import ch.zuehlke.f1telemetrytransformer.service.WebsocketService;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final WebsocketService websocketService;

    @KafkaListener(topics = "${kafka.topics.output.laptimeupdate}", containerFactory = "lapTimeUpdateListenerContainerFactory")
    public void consumeLapTimeMessage(@Payload LapTimeUpdate message) {
        LOGGER.info("Consuming LapTimeUpdate Topic: " + message);
        websocketService.sendLaptimeUpdate(message);
    }

    @KafkaListener(topics = "${kafka.topics.input.telemetryupdate}", containerFactory = "telemetryUpdateListenerContainerFactory")
    public void consumeTelemetryMessage(@Payload TelemetryUpdateMessage message) {
        LOGGER.info("Consuming TelemetryUpdate Topic: " + message);
        websocketService.sendTelemetryUpdate(message);
    }
}
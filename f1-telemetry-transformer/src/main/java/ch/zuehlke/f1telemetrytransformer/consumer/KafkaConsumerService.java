package ch.zuehlke.f1telemetrytransformer.consumer;

import ch.zuehlke.f1telemetrytransformer.model.LapTimeMessage;
import ch.zuehlke.f1telemetrytransformer.model.TemperatureMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService{
    private final SimpMessagingTemplate template;

    public KafkaConsumerService(SimpMessagingTemplate template) {
        this.template = template;
    }

    @KafkaListener(topics="${kafka.topic}", containerFactory = "temperatureListenerContainerFactory")
    public void consume(@Payload TemperatureMessage message) {
        System.out.println(message);
        template.convertAndSend("/topic/temperature", message.getTemperature());
    }

    @KafkaListener(topics="${kafka.topics.laptime}", containerFactory = "lapTimeListenerContainerFactory")
    public void consume(@Payload LapTimeMessage message) {
        System.out.println(message);
        template.convertAndSend("/topic/laptimes", message);
    }
}
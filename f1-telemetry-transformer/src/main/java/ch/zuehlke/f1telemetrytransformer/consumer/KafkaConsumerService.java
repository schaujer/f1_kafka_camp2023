package ch.zuehlke.f1telemetrytransformer.consumer;

import ch.zuehlke.f1telemetrytransformer.model.LapTimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService{
    private final SimpMessagingTemplate template;

    @KafkaListener(topics="${kafka.topics.laptime}", containerFactory = "lapTimeListenerContainerFactory")
    public void consume(@Payload LapTimeMessage message) {
        System.out.println(message);
        template.convertAndSend("/topic/laptimes", message);
    }
}
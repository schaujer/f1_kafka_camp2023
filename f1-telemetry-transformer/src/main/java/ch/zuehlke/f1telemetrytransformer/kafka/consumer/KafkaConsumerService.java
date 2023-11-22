package ch.zuehlke.f1telemetrytransformer.kafka.consumer;

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
        System.out.println("Consuming laptimeupdate:" + message);
        websocketService.sendLaptimeUpdate(message);
    }

    //@KafkaListener(topics="${kafka.input.topics.sectorupdate}", containerFactory = "sectorUpdateListenerContainerFactory")
    //public void consumeSectorUpdateMessage(@Payload SectorUpdateMessage message) {
    //    System.out.println(message);
    //    websocketService.sendLaptimeUpdate(LapTimeUpdate.builder().build());
    //}
}
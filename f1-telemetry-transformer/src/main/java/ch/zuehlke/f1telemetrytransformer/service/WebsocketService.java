package ch.zuehlke.f1telemetrytransformer.service;

import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final static String lapTimeTopic = "/topic/laptimes";
    private final SimpMessagingTemplate template;

    public void sendLaptimeUpdate(LapTimeUpdate update) {
        template.convertAndSend(lapTimeTopic, update);
    }
}

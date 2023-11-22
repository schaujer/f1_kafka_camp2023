package ch.zuehlke.f1telemetrytransformer.controller;

import ch.zuehlke.f1telemetrytransformer.service.LapTimeService;
import ch.zuehlke.f1telemetrytransformer.service.model.LapTimeUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LapTimeController {
    private final LapTimeService lapTimeService;

    @GetMapping("/currenttimings")
    public List<LapTimeUpdate> getCurrentLapTimeState() {
        return lapTimeService.getCurrentLapTimeUpdates();
    }
}

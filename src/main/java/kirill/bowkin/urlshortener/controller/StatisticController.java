package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.dto.StatOneResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    @GetMapping("/stats/${shortName}")
    public StatOneResponseDto statForOne() {
        return null;
    }
}

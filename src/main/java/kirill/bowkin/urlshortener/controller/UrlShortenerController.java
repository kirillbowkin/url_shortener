package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.dto.GenerateRequestDto;
import kirill.bowkin.urlshortener.dto.GenerateResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    @PostMapping("/generate")
    public GenerateResponseDto generateShortUrl(@RequestBody GenerateRequestDto requestDto) {

    }
}

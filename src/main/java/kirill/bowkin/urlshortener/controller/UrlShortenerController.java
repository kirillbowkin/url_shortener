package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.dto.GenerateRequestDto;
import kirill.bowkin.urlshortener.dto.GenerateResponseDto;
import kirill.bowkin.urlshortener.entity.UrlsEntity;
import kirill.bowkin.urlshortener.exception.UrlFailedToSaveException;
import kirill.bowkin.urlshortener.service.shortUrlGenerator.ShortUrlGenerator;
import kirill.bowkin.urlshortener.service.urlValidator.UrlValidator;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UrlShortenerController {

    private final UrlValidator urlValidator;
    private final ShortUrlGenerator shortUrlGenerator;
    private final UrlsService urlsService;
    private final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);


    public UrlShortenerController(UrlValidator urlValidator, ShortUrlGenerator shortUrlGenerator, UrlsService urlsService) {
        this.urlValidator = urlValidator;
        this.shortUrlGenerator = shortUrlGenerator;
        this.urlsService = urlsService;
    }


    @PostMapping("/generate")
    public GenerateResponseDto generateShortUrl(@RequestBody GenerateRequestDto requestDto) {
        String originalUrl = requestDto.original();
        boolean isValid = urlValidator.validate(originalUrl);

        if(!isValid) {
            logger.error("Url {} is invalid", originalUrl);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid url");
        }

        try {
            UrlsEntity savedEntity = urlsService.save(originalUrl);
            logger.info("Generated short url {} for {}", savedEntity.getShortUrl(), originalUrl);
            return new GenerateResponseDto(savedEntity.getShortUrl());
        } catch (UrlFailedToSaveException e) {
            logger.info("Failed to save url {}", originalUrl);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save url", e.getCause());
        }

    }
}

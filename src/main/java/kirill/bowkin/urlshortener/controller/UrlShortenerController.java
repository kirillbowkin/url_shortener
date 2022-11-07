package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.dto.GenerateRequestDto;
import kirill.bowkin.urlshortener.dto.GenerateResponseDto;
import kirill.bowkin.urlshortener.entity.UrlsEntity;
import kirill.bowkin.urlshortener.exception.UrlFailedToSaveException;
import kirill.bowkin.urlshortener.exception.UrlInvalidException;
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

    private final ShortUrlGenerator shortUrlGenerator;
    private final UrlsService urlsService;
    private final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);


    public UrlShortenerController(UrlValidator urlValidator, ShortUrlGenerator shortUrlGenerator, UrlsService urlsService) {
        this.shortUrlGenerator = shortUrlGenerator;
        this.urlsService = urlsService;
    }


    @PostMapping("/generate")
    public GenerateResponseDto generateShortUrl(@RequestBody GenerateRequestDto requestDto) {
        String originalUrl = requestDto.original();
        try {
            UrlsEntity savedEntity = urlsService.saveUrl(requestDto);
            logger.info("IN generateShortUrl - Generated short url {} for {}", savedEntity.getShortUrl(), originalUrl);
            return new GenerateResponseDto(savedEntity.getShortUrl());
        } catch (UrlInvalidException e) {
            logger.error("IN generateShortUrl - Url {} is invalid", originalUrl);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid url", e.getCause());
        } catch (UrlFailedToSaveException e) {
            logger.error("IN generateShortUrl - Failed to save url {}", originalUrl);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save url", e.getCause());
        }
    }
}

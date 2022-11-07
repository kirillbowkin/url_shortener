package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.dto.StatOneResponseDto;
import kirill.bowkin.urlshortener.exception.UrlDoesNotExistsException;
import kirill.bowkin.urlshortener.service.stat.StatService;
import kirill.bowkin.urlshortener.service.urlValidator.UrlValidator;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import kirill.bowkin.urlshortener.view.UrlsWithRankView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StatisticController {

    private final UrlsService urlsService;
    private final Logger logger = LoggerFactory.getLogger(StatisticController.class);
    private final UrlValidator urlValidator;
    private final StatService statService;


    @Value("${hostname}")
    private String hostname;

    public StatisticController(UrlsService urlsService, UrlValidator urlValidator, StatService statService) {
        this.urlsService = urlsService;
        this.urlValidator = urlValidator;
        this.statService = statService;
    }

    @GetMapping("/stats/{shortName}")
    public StatOneResponseDto statForOne(@PathVariable("shortName") String shortName) {
        try {
            return statService.statForOne(shortName);
        } catch (UrlDoesNotExistsException e) {
            logger.error("IN statForOne - url for {} does not exists", shortName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "url for " + shortName + " does not exists", e.getCause());
        }
    }

    @GetMapping("/stats")
    public List<StatOneResponseDto> statForAll(@RequestParam("page") int page, @RequestParam("count") int count) {
        return statService.statForAll(page, count);
    }
}

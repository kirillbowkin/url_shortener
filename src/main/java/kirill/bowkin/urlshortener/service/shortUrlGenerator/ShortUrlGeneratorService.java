package kirill.bowkin.urlshortener.service.shortUrlGenerator;


import kirill.bowkin.urlshortener.dto.GenerateRequestDto;
import kirill.bowkin.urlshortener.exception.UrlInvalidException;
import kirill.bowkin.urlshortener.service.stringShortener.StringShortener;
import kirill.bowkin.urlshortener.service.urlBuilder.UrlBuilder;
import kirill.bowkin.urlshortener.service.urlValidator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShortUrlGeneratorService implements ShortUrlGenerator {

    private final Logger logger = LoggerFactory.getLogger(ShortUrlGeneratorService.class);
    private final StringShortener stringShortener;
    private final UrlBuilder urlBuilder;
    private final UrlValidator urlValidator;

    @Value("${hostname}")
    private String hostname;

    public ShortUrlGeneratorService(StringShortener stringShortener, UrlBuilder urlBuilder, UrlValidator urlValidator) {
        this.stringShortener = stringShortener;
        this.urlBuilder = urlBuilder;
        this.urlValidator = urlValidator;
    }

    @Override
    public String generateShortUrl(String url) throws UrlInvalidException {
        if(!urlValidator.isValid(url)) {
            throw new UrlInvalidException("Url " + url + " " + "is invalid");
        }

        String shortenedString = stringShortener.shortenString(url);
        String shortUrl = urlBuilder
                .setDelimiter("/l/")
                .setShortenedString(shortenedString)
                .build();
        logger.info("IN generateShortUrl - Url {} was shortened to {}", url, shortUrl);
        return shortUrl;
    }


}

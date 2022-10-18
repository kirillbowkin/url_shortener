package kirill.bowkin.urlshortener.service.shortUrlGenerator;


import kirill.bowkin.urlshortener.service.stringShortener.StringShortener;
import kirill.bowkin.urlshortener.service.urlBuilder.UrlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlGeneratorService implements ShortUrlGenerator {

    private final Logger logger = LoggerFactory.getLogger(ShortUrlGeneratorService.class);
    private final StringShortener stringShortener;
    @Value("${hostname}")
    private String hostname;

    public ShortUrlGeneratorService(StringShortener stringShortener) {
        this.stringShortener = stringShortener;
    }

    @Override
    public String generateShortUrl(String url) {
        String shortenedString = stringShortener.shortenString(url);
        String shortUrl = UrlBuilder.buildUrl(hostname, "/l/", shortenedString);
        logger.info("IN generateShortUrl - Url {} was shortened to {}", url , shortUrl);
        return shortUrl;
    }


}

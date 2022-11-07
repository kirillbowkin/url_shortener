package kirill.bowkin.urlshortener.service.redirect;

import kirill.bowkin.urlshortener.entity.UrlsEntity;
import kirill.bowkin.urlshortener.exception.UrlDoesNotExistsException;
import kirill.bowkin.urlshortener.service.urlBuilder.UrlBuilder;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Service
public class RedirectService {

    private final UrlsService urlsService;
    private final Logger logger = LoggerFactory.getLogger(RedirectService.class);

    public RedirectService(UrlsService urlsService) {
        this.urlsService = urlsService;
    }

    public String redirect(String shortName) throws UrlDoesNotExistsException {
        String shortUrl = new UrlBuilder()
                .setDelimiter("/l/")
                .setShortenedString(shortName)
                .build();

        Optional<UrlsEntity> urlsEntity = urlsService.findUrl(shortUrl);
        if (urlsEntity.isPresent()) {
            urlsService.incrementUrlCounter(shortUrl);
            return urlsEntity.get().getUrl();
        } else {
            logger.error("Url for {} does not exists");
            throw new UrlDoesNotExistsException("Url for " + shortUrl + " does not exists");
        }

    }

}

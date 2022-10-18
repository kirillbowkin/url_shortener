package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.entity.UrlsEntity;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class RedirectController {

    private final UrlsService urlsService;
    private final Logger logger = LoggerFactory.getLogger(RedirectController.class);

    public RedirectController(UrlsService urlsService) {
        this.urlsService = urlsService;
    }

    @GetMapping("/l/{shortUrl}")
    public RedirectView redirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String shortUrl = httpServletRequest.getRequestURL().toString();
        Optional<UrlsEntity> urlsEntity = urlsService.find(shortUrl);
        if (urlsEntity.isPresent()) {
            urlsService.incrementCounter(shortUrl);

            String originalUrl = urlsEntity.get().getUrl();
            httpServletResponse.addHeader("Cache-Control", "max-age=60, must-revalidate, no-transform");

            logger.info("Redirecting from {} to {}", shortUrl, originalUrl);
            return new RedirectView(originalUrl);
        }

        logger.error("Short url {} doesn't exist", shortUrl);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "short url doesn't exist");
    }
}

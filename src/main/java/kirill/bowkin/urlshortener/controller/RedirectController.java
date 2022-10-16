package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.entity.UrlsEntity;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class RedirectController {

    private final UrlsService urlsService;

    public RedirectController(UrlsService urlsService) {
        this.urlsService = urlsService;
    }

    @GetMapping("/l/{shortUrl}")
    public RedirectView redirect(HttpServletRequest httpServletRequest) {
        String shortUrl = httpServletRequest.getRequestURL().toString();
        Optional<UrlsEntity> urlsEntity = urlsService.find(shortUrl);
        if (urlsEntity.isPresent()) {
            String originalUrl = urlsEntity.get().getUrl();
            return new RedirectView(originalUrl);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "short url doesn't exist");
    }
}

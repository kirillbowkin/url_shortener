package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.exception.UrlDoesNotExistsException;
import kirill.bowkin.urlshortener.service.redirect.RedirectService;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RedirectController {

    private final UrlsService urlsService;
    private final Logger logger = LoggerFactory.getLogger(RedirectController.class);
    private final RedirectService redirectService;

    public RedirectController(UrlsService urlsService, RedirectService redirectService) {
        this.urlsService = urlsService;
        this.redirectService = redirectService;
    }

    @GetMapping("/l/{shortName}")
    public RedirectView redirect(@PathVariable("shortName") String shortName, HttpServletResponse httpServletResponse) {
        try {
            String redirectUrl = redirectService.redirect(shortName);
            httpServletResponse.addHeader("Cache-Control", "max-age=60, must-revalidate, no-transform");
            logger.info("IN redirect - Redirecting from {} to {}", shortName, redirectUrl);
            return new RedirectView(redirectUrl);
        } catch (UrlDoesNotExistsException e) {
            logger.error("IN redirect - Short url {} doesn't exist", shortName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "short url doesn't exist");
        }

    }
}

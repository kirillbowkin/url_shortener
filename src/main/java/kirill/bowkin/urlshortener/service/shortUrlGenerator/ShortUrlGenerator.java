package kirill.bowkin.urlshortener.service.shortUrlGenerator;


import kirill.bowkin.urlshortener.dto.GenerateRequestDto;
import kirill.bowkin.urlshortener.exception.UrlInvalidException;

public interface ShortUrlGenerator {
    String generateShortUrl(String url) throws UrlInvalidException;
}

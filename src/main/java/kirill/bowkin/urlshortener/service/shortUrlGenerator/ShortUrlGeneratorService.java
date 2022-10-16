package kirill.bowkin.urlshortener.service.shortUrlGenerator;


import kirill.bowkin.urlshortener.service.stringShortener.StringShortener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlGeneratorService implements ShortUrlGenerator {

    private final StringShortener stringShortener;
    @Value("${hostname")
    private String hostname;

    public ShortUrlGeneratorService(StringShortener stringShortener) {
        this.stringShortener = stringShortener;
    }

    @Override
    public String generateShortUrl(String url) {
        String shortenedString = stringShortener.shortenString(url);
        return "http://" + hostname + "/l/" + shortenedString;
    }
}

package kirill.bowkin.urlshortener.service.urlValidator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlValidatorService implements UrlValidator {
    @Override
    public boolean verify(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}

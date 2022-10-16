package kirill.bowkin.urlshortener.service.urlValidator;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UrlValidatorService implements UrlValidator {
    private final String URL_PATTERN = "^(?:(?:http(?:s)?|ftp)://)(?:\\S+(?::(?:\\S)*)?@)?(?:(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)(?:\\.(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)*(?:\\.(?:[a-z0-9\u00a1-\uffff]){2,})(?::(?:\\d){2,5})?(?:/(?:\\S)*)?$";

    @Override
    public boolean validate(String url) {
        return Pattern.compile(URL_PATTERN).matcher(url).matches();
    }
}

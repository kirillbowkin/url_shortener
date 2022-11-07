package kirill.bowkin.urlshortener.service.urlValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UrlValidatorService implements UrlValidator {
    private final String URL_PATTERN = "^(?:(?:http(?:s)?|ftp)://)(?:\\S+(?::(?:\\S)*)?@)?(?:(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)(?:\\.(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)*(?:\\.(?:[a-z0-9\u00a1-\uffff]){2,})(?::(?:\\d){2,5})?(?:/(?:\\S)*)?$";
    private final Logger logger = LoggerFactory.getLogger(UrlValidatorService.class);

    @Override
    public boolean isValid(String url) {
        boolean isValid = Pattern.compile(URL_PATTERN).matcher(url).matches();
        if (isValid) logger.info("Url {} is valid", url);
        else logger.error("IN isValid - Url {} is not valid", url);
        return isValid;
    }
}

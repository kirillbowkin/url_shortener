package kirill.bowkin.urlshortener.service.urlBuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlBuilder {

    private static final Logger logger = LoggerFactory.getLogger(UrlBuilder.class);
    public static String buildUrl(String hostname, String delimiter, String shortenedString) {
        String url = hostname + delimiter + shortenedString;
        logger.info("IN buildUrl - From hostname: {}, delimiter: {}, shortenedString: {} - built url {}", hostname, delimiter, shortenedString, url);
        return url;
    }
}

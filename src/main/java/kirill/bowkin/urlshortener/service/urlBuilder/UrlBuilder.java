package kirill.bowkin.urlshortener.service.urlBuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UrlBuilder {

    private final Logger logger = LoggerFactory.getLogger(UrlBuilder.class);
    private String hostname;
    private String delimiter;
    private String shortenedString;

    public UrlBuilder setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public UrlBuilder setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public UrlBuilder setShortenedString(String shortenedString) {
        this.shortenedString = shortenedString;
        return this;
    }

    public String build() {
        String url = hostname + delimiter + shortenedString;
        logger.info("IN buildUrl - From hostname: {}, delimiter: {}, shortenedString: {} - built url {}", hostname, delimiter, shortenedString, url);
        return url;
    }
}

package kirill.bowkin.urlshortener.service.stringShortener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

@Service
public class StringShortener {
    private final MessageDigest messageDigest;
    private final int urlLength = 5;
    private final Logger logger = LoggerFactory.getLogger(StringShortener.class);

    public StringShortener(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    public String shortenString(String s) {
        messageDigest.update(s.getBytes());
        byte[] digest = messageDigest.digest();
        String shortString = DatatypeConverter.printHexBinary(digest).toLowerCase().substring(0, urlLength);
        logger.info("IN shortenString - For {} short string is {}", s, shortString);
        return shortString;
    }
}

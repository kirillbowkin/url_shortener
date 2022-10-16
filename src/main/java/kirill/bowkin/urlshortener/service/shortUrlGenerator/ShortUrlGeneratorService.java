package kirill.bowkin.urlshortener.service.shortUrlGenerator;


import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

@Service
public class ShortUrlGeneratorService implements ShortUrlGenerator {

    private final MessageDigest messageDigest;
    private final int urlLength = 5;

    public ShortUrlGeneratorService(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    @Override
    public String shorten(String url) {
        messageDigest.update(url.getBytes());
        byte[] digest = messageDigest.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase().substring(0, urlLength);
    }
}

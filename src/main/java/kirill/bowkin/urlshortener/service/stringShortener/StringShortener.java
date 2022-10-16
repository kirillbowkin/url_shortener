package kirill.bowkin.urlshortener.service.stringShortener;


import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

@Service
public class StringShortener {
    private final MessageDigest messageDigest;
    private final int urlLength = 5;

    public StringShortener(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    public String shortenString(String s) {
        messageDigest.update(s.getBytes());
        byte[] digest = messageDigest.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase().substring(0, urlLength);
    }
}

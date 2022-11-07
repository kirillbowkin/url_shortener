package kirill.bowkin.urlshortener.service.stringShortener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringShortenerTest {
    StringShortener stringShortener;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        stringShortener = new StringShortener(MessageDigest.getInstance("MD5"));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/url_short_test.csv")
    void shouldPassIfShortensCorrectly(String s, String shortenedString) {
        String shortened = stringShortener.shortenString(s);
        assertEquals(shortenedString, shortened);
    }
}
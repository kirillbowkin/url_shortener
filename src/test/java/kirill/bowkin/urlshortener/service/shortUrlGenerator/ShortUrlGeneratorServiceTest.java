package kirill.bowkin.urlshortener.service.shortUrlGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class ShortUrlGeneratorServiceTest {

    ShortUrlGeneratorService shortUrlGeneratorService;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        shortUrlGeneratorService = new ShortUrlGeneratorService(MessageDigest.getInstance("MD5"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/url_short_test.csv")
    void shouldPassIfShortensCorrectly(String url, String shortenedUrl) {
        String shortened = shortUrlGeneratorService.shorten(url);
        assertEquals(shortenedUrl, shortened);
    }
}
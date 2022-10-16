package kirill.bowkin.urlshortener.service.urlValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class UrlValidatorServiceTest {

    private final UrlValidatorService urlValidatorService = new UrlValidatorService();

    @ParameterizedTest
    @CsvFileSource(resources = "/valid_urls.csv")
    void shouldReturnTrueIfUrlIsValid(String url) {
        boolean isValid = urlValidatorService.verify(url);
        assertTrue(isValid);
    }
}
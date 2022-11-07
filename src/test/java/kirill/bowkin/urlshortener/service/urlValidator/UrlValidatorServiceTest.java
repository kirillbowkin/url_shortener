package kirill.bowkin.urlshortener.service.urlValidator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class UrlValidatorServiceTest {

    private final UrlValidatorService urlValidatorService = new UrlValidatorService();

    @ParameterizedTest
    @CsvFileSource(resources = "/valid_urls.csv")
    void shouldReturnTrueIfUrlIsValid(String url) {
        boolean isValid = urlValidatorService.isValid(url);
        assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalid_urls.csv")
    void shouldReturnFalseIfUrlIsInvalid(String url) {
        boolean isValid = urlValidatorService.isValid(url);
        assertFalse(isValid);
    }
}
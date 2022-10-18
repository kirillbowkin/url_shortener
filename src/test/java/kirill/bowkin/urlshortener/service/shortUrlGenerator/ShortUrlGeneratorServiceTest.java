package kirill.bowkin.urlshortener.service.shortUrlGenerator;

import kirill.bowkin.urlshortener.service.stringShortener.StringShortener;
import kirill.bowkin.urlshortener.service.urlBuilder.UrlBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ShortUrlGeneratorServiceTest {

    ShortUrlGeneratorService shortUrlGeneratorService;
    private final String hostnameValue = "localhost:8080";

    @Mock
    private StringShortener stringShortener;
    private UrlBuilder urlBuilder = Mockito.mock(UrlBuilder.class, Mockito.RETURNS_DEEP_STUBS);


    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        shortUrlGeneratorService = new ShortUrlGeneratorService(stringShortener, urlBuilder);
        Field hostname = shortUrlGeneratorService.getClass().getDeclaredField("hostname");
        hostname.setAccessible(true);
        hostname.set(shortUrlGeneratorService, hostnameValue);
    }

    @Test
    void shouldReturnTrueIfGeneratesUrlCorrectly() {
        String url = "http://google.com";
        String mockedShortenedString = "12abc";
        Mockito.when(stringShortener.shortenString(url)).thenReturn(mockedShortenedString);
        Mockito.when(urlBuilder.setHostname(hostnameValue).setDelimiter("/l/").setShortenedString(mockedShortenedString).build()).thenReturn(hostnameValue + "/l/" + mockedShortenedString);

        String shortUrl = shortUrlGeneratorService.generateShortUrl(url);
        String expectedUrl = hostnameValue + "/l/" + mockedShortenedString;
        assertEquals(expectedUrl, shortUrl);

    }
}
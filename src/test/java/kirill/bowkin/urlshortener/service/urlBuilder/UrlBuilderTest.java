package kirill.bowkin.urlshortener.service.urlBuilder;

import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.junitpioneer.jupiter.cartesian.CartesianTest.Values;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlBuilderTest {

    @CartesianTest()
    void ShouldPassIfUrlIsBuiltCorrectly(@Values(strings = {"google.com", "amazon.com", "netflix.com"}) String hostname,
                                         @Values(strings = {"/l/", "/link/"}) String delimiter,
                                         @Values(strings = {"fsd34", "3kl4s", "90dfs"}) String shortenedString) {
        String url = UrlBuilder.buildUrl(hostname, delimiter, shortenedString);

        assertEquals(hostname + delimiter + shortenedString, url);
    }
}
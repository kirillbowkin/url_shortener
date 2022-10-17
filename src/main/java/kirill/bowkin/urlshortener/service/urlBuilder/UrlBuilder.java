package kirill.bowkin.urlshortener.service.urlBuilder;


public class UrlBuilder {
    public static String buildUrl(String hostname, String delimiter, String shortenedString) {
        return "http://" + hostname + "/l/" + shortenedString;
    }
}

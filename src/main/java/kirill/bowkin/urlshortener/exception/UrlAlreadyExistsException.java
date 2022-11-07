package kirill.bowkin.urlshortener.exception;


public class UrlAlreadyExistsException extends Exception {
    public UrlAlreadyExistsException(String message) {
        super(message);
    }
}

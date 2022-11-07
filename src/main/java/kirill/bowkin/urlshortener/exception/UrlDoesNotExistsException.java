package kirill.bowkin.urlshortener.exception;

/**
 * @author Кирилл
 */
public class UrlDoesNotExistsException extends Exception {
    public UrlDoesNotExistsException(String message) {
        super(message);
    }
}

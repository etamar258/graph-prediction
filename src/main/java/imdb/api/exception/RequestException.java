package imdb.api.exception;

public class RequestException extends Exception {

    private static final long serialVersionUID = 6195891777755782695L;

    public RequestException(String msg) {
        super(msg);
    }

    public RequestException(Throwable throwObject) {
        super(throwObject);
    }

    public RequestException(String msg, Throwable throwObject) {
        super(msg, throwObject);
    }
}

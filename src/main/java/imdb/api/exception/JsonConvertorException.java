package imdb.api.exception;

public class JsonConvertorException extends Exception {

    private static final long serialVersionUID = 4366161366013514804L;

    public JsonConvertorException(String msg) {
        super(msg);
    }

    public JsonConvertorException(Throwable throwObject) {
        super(throwObject);
    }

    public JsonConvertorException(String msg, Throwable throwObject) {
        super(msg, throwObject);
    }

}

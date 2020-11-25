package imdb.api.requstobjects;

import imdb.api.exception.RequestException;

public abstract class RequestObject<T> {
    protected String urlAddress;

    public RequestObject(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public abstract void addParameter(T requestParameterType, String parameterToSend);

    public abstract String sendRequest() throws RequestException;

    public String getUrlAddress() {
        return urlAddress;
    }
}

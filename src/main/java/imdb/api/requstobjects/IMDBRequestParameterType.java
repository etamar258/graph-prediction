package imdb.api.requstobjects;

public enum IMDBRequestParameterType {
    SEARCH_BY_STRING("s="),
    SEARCH_BY_ID("i="),
    TITLE("t="),
    TYPE("type="),
    YEAR("y="),
    PLOT("plot="),
    DATA_TO_RETURN("r="),
    INCLUDE_TOMATOES_RATINGS("tomatoes=");

    private String parameterType;

    private IMDBRequestParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterType() {
        return parameterType;
    }
}

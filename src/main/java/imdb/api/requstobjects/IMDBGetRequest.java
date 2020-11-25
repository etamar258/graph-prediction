package imdb.api.requstobjects;

import imdb.api.exception.RequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IMDBGetRequest extends RequestObject<IMDBRequestParameterType> {
    private static final String PLUS = "+";
    private static final String SPACE = " ";
    private static final String START_PARAMETER_LIST = "?";
    private static final String ADD_OTHER_PARAMETER = "&";
    private static final String USER_AGENT = "Mozilla/5.0";

    private String parametersToSend;

    public IMDBGetRequest() {
        super("http://www.omdbapi.com/");
        parametersToSend = START_PARAMETER_LIST;
    }

    @Override
    public void addParameter(IMDBRequestParameterType requestParameterType, String parameterToSend) {
        if (parametersToSend.length() > 1) {
            parametersToSend += ADD_OTHER_PARAMETER;
        }

        parametersToSend += requestParameterType.getParameterType() +
                parameterToSend.replace(SPACE, PLUS);
    }

    @Override
    public String sendRequest() throws RequestException {
        if (parametersToSend.length() == 1) {
            throw new RequestException("Can't send request without parameters");
        }

        String fullUrlToSend = urlAddress + parametersToSend;
        URL urlObjectToSend;

        try {
            urlObjectToSend = new URL(fullUrlToSend);
            HttpURLConnection httpConnection =
                    (HttpURLConnection) urlObjectToSend.openConnection();

            // optional default is GET
            httpConnection.setRequestMethod("GET");

            //add request header
            httpConnection.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = httpConnection.getResponseCode();

            if (responseCode != 200) {
                throw new RequestException("Bad respone code: " + responseCode);
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        } catch (MalformedURLException e) {
            throw new RequestException("Bad url exception, The url: " + fullUrlToSend, e);
        } catch (IOException e) {
            throw new RequestException("Couldn't create http connection", e);
        } finally {
            resetRequest();
        }
    }

    public void resetRequest() {
        parametersToSend = START_PARAMETER_LIST;
    }

    public String getParametersList() {
        return parametersToSend;
    }
}

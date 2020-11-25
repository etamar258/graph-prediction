package imdb.api.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import imdb.api.exception.JsonConvertorException;

import java.io.IOException;
import java.util.List;

public abstract class JsonConvertor<T> {
    protected ObjectMapper jsonMapper;

    public JsonConvertor() {
        jsonMapper = new ObjectMapper();
        jsonMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public List<T> getJsonJavaObjectsList(String jsonInString) throws JsonConvertorException {
        try {
            return jsonMapper.readValue(jsonInString, new TypeReference<List<T>>() {
            });
        } catch (JsonParseException e) {
            throw new JsonConvertorException("Couldn't convert the json input to list of objects", e);
        } catch (JsonMappingException e) {
            throw new JsonConvertorException("Couldn't Map all the objects", e);
        } catch (IOException e) {
            throw new JsonConvertorException("Can't read the json string", e);
        }
    }

    public T getJsonJavaObject(String jsonInString) throws JsonConvertorException {
        try {
            return jsonMapper.readValue(jsonInString, getClassObject());
        } catch (JsonParseException e) {
            throw new JsonConvertorException("Couldn't convert the json input to list of objects", e);
        } catch (JsonMappingException e) {
            throw new JsonConvertorException("Couldn't Map all the objects", e);
        } catch (IOException e) {
            throw new JsonConvertorException("Can't read the json string", e);
        }
    }

    protected abstract Class<T> getClassObject();
}

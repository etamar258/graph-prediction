package seed;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import core.repositories.Repository;
import seed.resolvers.Resolver;

import java.io.IOException;
import java.io.InputStream;

public class Seeder<T> {
    protected ObjectMapper _objMapper;
    protected Repository<T> _repository;
    protected FormatSchema _schema;

    public Seeder(ObjectMapper objectMapper, FormatSchema schema, Repository<T> repository) {
        _objMapper = objectMapper;
        _schema = schema;
        _repository = repository;
    }

    /**
     * Seed a stream of data into a database.
     *
     * @param input    the input stream - could be a file, connection.
     * @param type     the type we can convert the input using an object mapper.
     * @param resolver an handler which converts 'type' into the wanted final object.
     * @throws IOException
     * @throws ClassCastException
     */
    public void seed(InputStream input, Class<?> type, Resolver<T> resolver) throws IOException, ClassCastException {
        ObjectReader reader = _objMapper.readerFor(type).with(_schema);
        MappingIterator<Object> it = reader.readValues(input);

        it.forEachRemaining((Object record) -> {
            // Resolve a record of type 'type' and creates a 'T' object
            // Sometimes we will need the repository in order to decide if we want to resolve the record or not
            // Or create relationships between resolved 'T' object and other objects
            T data = resolver.resolve(record);

            if (null != data) {
                _repository.createOrUpdate(data);
            }
        });
    }

}

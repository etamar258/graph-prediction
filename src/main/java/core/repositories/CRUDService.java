package core.repositories;

import java.util.Map;
import core.exceptions.DBQueryCreationException;

public interface CRUDService<T> {

    Iterable<T> findAll();

    T find(Long id);

    void delete(Long id);

    T createOrUpdate(T object);
    
    Iterable<T> findDBObject(Map<String, String> parameters)  throws DBQueryCreationException;
}

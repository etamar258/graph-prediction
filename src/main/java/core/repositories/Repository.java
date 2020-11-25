package core.repositories;

import core.entities.Entity;
import core.exceptions.DBQueryCreationException;
import core.utility.Neo4jSessionFactory;
import java.util.Map;
import java.util.Map.Entry;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;

public abstract class Repository<T> implements CRUDService<T> {
    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;
    private Session _session = Neo4jSessionFactory.getInstance().getSession();

    protected Repository() {
    }

    protected Session getSession() {
        return _session;
    }

    public Iterable<T> findAll() {
        return _session.loadAll(getEntityType(), DEPTH_LIST);
    }

    public T find(Long id) {
        return _session.load(getEntityType(), id, DEPTH_ENTITY);
    }

    public void delete(Long id) {
        _session.delete(_session.load(getEntityType(), id));
    }

    public T createOrUpdate(T entity) {
        _session.save(entity, DEPTH_ENTITY);
        T data = find(((Entity) entity).getId());
        return data;
    }

    public T getFirstObject(Map<String, String> parameters)
    {
    	try 
        {
			Iterable<T> searchObjects = findDBObject(parameters);
			
			if(!searchObjects.iterator().hasNext())
			{
				return null;
			}
			
			return searchObjects.iterator().next();
		} 
        catch (DBQueryCreationException e) 
        {
			System.err.println("Can't get movie object" + e);
		}
        
        return null;
    }
    
    public Iterable<T> findDBObject(Map<String, String> parameters) throws DBQueryCreationException
    {
    	String queryToExecute = getQueryToExecute(parameters);
    	return _session.query(getEntityType(), queryToExecute, parameters);
    }
    
    public Result executeQuery(String queryToExecute, Map<String, String> parameters)
    {
    	return _session.query(queryToExecute, parameters);
    }
    
    public Iterable<T> executeQueryWitoutParameters(String queryToExecute)
    {
    	Map<String, Object> parameters = new java.util.HashMap<>();
    	return _session.query(getEntityType(), queryToExecute, parameters);
    }
    
    private String getQueryToExecute(Map<String, String> parameters) throws DBQueryCreationException 
    {
    	if(parameters.isEmpty())
    	{
    		throw new DBQueryCreationException("Can't create query without parameters");
    	}
    	
    	
		String resultQuery = "MATCH (obj:" + getObjectName() + ") WHERE ";
		
		for (Entry<String, String> currParam : parameters.entrySet()) 
		{
			resultQuery += "LOWER(obj." + currParam.getKey() + ")=LOWER({" + currParam.getKey() + "}) AND";
			
		}
		
		resultQuery += " 1=1 RETURN (obj)";
		
		return resultQuery;
	}

	public abstract Class<T> getEntityType();
	
	public abstract String getObjectName();
}

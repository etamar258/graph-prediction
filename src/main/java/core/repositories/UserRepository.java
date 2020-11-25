package core.repositories;

import core.entities.DbType;
import core.entities.User;
import java.util.HashMap;
import java.util.Map;

public class UserRepository extends Repository<User> 
{

    private static final String DBID_PARAMETER = "dbId";
	private static final String TYPE_PARAMETER = "type";

	public User getUserByDbID(DbType type, long dbId) 
	{
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(TYPE_PARAMETER, String.valueOf(type));
        parameters.put(DBID_PARAMETER, String.valueOf(dbId));
        
        return getFirstObject(parameters);
    }
	
    @Override
    public Class<User> getEntityType() 
    {
        return User.class;
    }

	@Override
	public String getObjectName() 
	{
		return "User";
	}
}

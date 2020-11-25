package core.repositories;

import core.entities.UserSimilarty;


public class UserSimilartyRepository extends Repository<UserSimilarty>
{

	@Override
	public Class<UserSimilarty> getEntityType() 
	{
		return UserSimilarty.class;
	}

	@Override
	public String getObjectName() 
	{
		return "User";
	}

}

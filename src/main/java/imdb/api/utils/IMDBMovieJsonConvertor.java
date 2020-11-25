package imdb.api.utils;

import imdb.api.jsonobjects.IMDBMovieObject;

public class IMDBMovieJsonConvertor extends JsonConvertor<IMDBMovieObject>
{

	@Override
	protected Class<IMDBMovieObject> getClassObject() 
	{
		return IMDBMovieObject.class;
	}
}

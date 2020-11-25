package core.exceptions;

public class DBQueryCreationException extends Exception
{

	private static final long serialVersionUID = -5431989170540364710L;
	
	
	public DBQueryCreationException(String exceptionMessage) 
	{
		super(exceptionMessage);
	}
	
	public DBQueryCreationException(String exceptionMessage, Throwable exceptionThrow) 
	{
		super(exceptionMessage, exceptionThrow);
	}
}

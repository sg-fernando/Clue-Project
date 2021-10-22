package cluegame;

public class BadConfigFormatException extends Exception
{
	private static final long serialVersionUID = 1L;

	public BadConfigFormatException()
	{
		super("Error: bad file config");
	}
	public BadConfigFormatException(String message)
	{
		super(message);
	}


}

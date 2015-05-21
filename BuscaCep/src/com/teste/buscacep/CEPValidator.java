package com.teste.buscacep;

public class CEPValidator 
{
	private static CEPValidator instance = null;
	private static int MAX_CHARS_LENGTH = 9;

	public static int EMPTY = 1;
	public static int INCOMPLETE = 2;
	public static int VALID = 3;
		
	protected CEPValidator() 
	{
		// Exists only to defeat instantiation.
	}

	public static CEPValidator getInstance() 
	{
		if(instance == null) 
		{
			instance = new CEPValidator();
		}
		return instance;
	}
	
	public int validate(String text)
	{
		if(text.length() == 0)
		{
			return EMPTY;
		}
		else if(text.length() < MAX_CHARS_LENGTH)
		{
			return INCOMPLETE;
		}
		else
		{
			return VALID;
		}
	}
}
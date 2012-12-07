/*
 * $Id$
 * 
 * Filename : GOException.java 
 * Project  : GOLink_API
 */
package br.golink;

/**
 * Class responsable for the GOLink exceptions. 
 *
 * @author Roque Pinel
 */
public class GOException extends Exception 
{
	/**
	 * Serial vesion.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new GOException.
	 * 
	 * @param exception
	 */
	public GOException(Throwable exception)
	{
		super(exception);
	}

	/**
	 * Create a new GOException.
	 * 
	 * @param message
	 */
	public GOException(String message)
	{
		super(message);
	}

	/**
	 * Create a new GOException.
	 * 
	 * @param message
	 * @param exception
	 */
	public GOException(String message, Throwable exception)
	{
		super(message, exception);
	}
}

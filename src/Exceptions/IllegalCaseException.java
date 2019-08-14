package Exceptions;

/**
 * A simple Exception.
 * @author Thielen M.
*/
public class IllegalCaseException extends Exception
{
	private static final long serialVersionUID = 5111285113504251577L;
	
	/**
	 * The constructor of the class.
	 * @param message -
	 * The message which will be shown
	 */
	public IllegalCaseException(String message)
	{
		super(message);
	}
}

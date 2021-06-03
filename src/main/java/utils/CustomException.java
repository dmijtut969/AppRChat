package utils;

/**
 * Class CustomException.
 * @author Daniel Mijens Tutor
 */
public class CustomException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new custom exception.
	 *
	 * @param mensaje the mensaje
	 */
	public CustomException(String mensaje) {
		super(mensaje);
	}
}

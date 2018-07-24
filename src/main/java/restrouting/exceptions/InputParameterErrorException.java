package restrouting.exceptions;


public class InputParameterErrorException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InputParameterErrorException() {
        super();

    }

    public InputParameterErrorException(String message) {
        super(message);
    }

}

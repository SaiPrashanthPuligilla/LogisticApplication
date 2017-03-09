package exceptions;

public class FacilityException extends OrderProcessingException {
	private static final long serialVersionUID = 1L;

	public FacilityException(String message) {
		System.err.println("Facility Parsing Exception :"+ message);
		System.exit(-1);
	}
}

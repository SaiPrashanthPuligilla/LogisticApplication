package exceptions;

public class NetworkException extends OrderProcessingException {

	private static final long serialVersionUID = 1L;

	public NetworkException(String message) {
		System.err.println("Facility Network Parsing Exception:"+ message);
		System.exit(-1);
	}

}

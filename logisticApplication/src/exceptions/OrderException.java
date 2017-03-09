package exceptions;

public class OrderException extends OrderProcessingException {

	private static final long serialVersionUID = 1L;

	public OrderException(String message) {
		System.err.println("Orders Parsing Exception:"+ message);
		System.exit(-1);
	}
}

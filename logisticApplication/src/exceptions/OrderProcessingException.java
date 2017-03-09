package exceptions;

public class OrderProcessingException extends Exception {
	private static final long serialVersionUID = 1L;

	public OrderProcessingException() {
		super();
	}

	public OrderProcessingException(String message) {
		super(message);
	}
}

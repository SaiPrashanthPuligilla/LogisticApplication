package exceptions;

public class ItemException extends OrderProcessingException {

	private static final long serialVersionUID = 1L;

	public ItemException(String message) {
		System.err.println("Item Parsing Exception :"+ message);
		System.exit(-1);
	}

}

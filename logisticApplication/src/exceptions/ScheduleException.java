package exceptions;

public class ScheduleException extends OrderProcessingException{

	private static final long serialVersionUID = 1L;

	public ScheduleException(String message) {
		System.err.println("Orders Parsing Exception:"+ message);
		System.exit(-1);
	}
}

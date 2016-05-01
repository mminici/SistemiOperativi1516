package it.sisop1516.stringspace;

public class ExceededStringBoundException extends Exception {
	private static final long serialVersionUID = -8364574259027224192L;
	
	public ExceededStringBoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

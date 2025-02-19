package exceptions;

public class ContaDesativadaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ContaDesativadaException(String message) {
        super(message);
    }
}



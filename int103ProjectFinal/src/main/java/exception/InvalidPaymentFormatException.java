package exception;

public class InvalidPaymentFormatException extends RuntimeException {
    public InvalidPaymentFormatException() {super();}
    public InvalidPaymentFormatException(String message) {super(message);}
    public InvalidPaymentFormatException(Throwable cause) {super(cause);}
    public InvalidPaymentFormatException(String message, Throwable cause)
    {super(message, cause);}
}

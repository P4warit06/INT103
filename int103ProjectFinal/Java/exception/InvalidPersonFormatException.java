package exception;

public class InvalidPersonFormatException extends RuntimeException {
    public InvalidPersonFormatException() {super();}
    public InvalidPersonFormatException(String message) {super(message);}
    public InvalidPersonFormatException(Throwable cause) {super(cause);}
    public InvalidPersonFormatException(String message, Throwable cause) {super(message, cause);}
}

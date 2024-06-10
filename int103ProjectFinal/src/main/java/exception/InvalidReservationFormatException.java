package exception;

public class InvalidReservationFormatException extends RuntimeException {
    public InvalidReservationFormatException() {super();}
    public InvalidReservationFormatException(String message) {super(message);}
    public InvalidReservationFormatException(Throwable cause) {super(cause);}
    public InvalidReservationFormatException(String message, Throwable cause) {super(message, cause);}
}

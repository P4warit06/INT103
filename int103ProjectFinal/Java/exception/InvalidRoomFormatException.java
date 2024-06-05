package exception;

public class InvalidRoomFormatException extends RuntimeException {
    public InvalidRoomFormatException() {super();}
    public InvalidRoomFormatException(String message) {super(message);}
    public InvalidRoomFormatException(Throwable cause) {super(cause);}
    public InvalidRoomFormatException(String message, Throwable cause) {super(message, cause);}
}

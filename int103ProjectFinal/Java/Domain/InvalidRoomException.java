package Domain;

public class InvalidRoomException extends  RuntimeException{
    public InvalidRoomException() {
    }

    public InvalidRoomException(String message) {
        super(message);
    }

    public InvalidRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRoomException(Throwable cause) {
        super(cause);
    }
}

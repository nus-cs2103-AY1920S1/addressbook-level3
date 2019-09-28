package seedu.address;

public abstract class AlfredException extends Exception {


    public AlfredException(String message) {
        super(message);
    }

    public AlfredException(String message, Throwable cause) {
        super(message, cause);
    }


    public AlfredException(Exception cause) {
        super(cause);
    }


}


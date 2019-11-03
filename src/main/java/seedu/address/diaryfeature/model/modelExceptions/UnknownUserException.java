package seedu.address.diaryfeature.model.modelExceptions;


public class UnknownUserException extends Exception {
    private final String ERROR_MESSAGE = "ALERT! YOU HAVE ALREADY SET YOUR DETAILS";

    public UnknownUserException() {
        super();
    }
    public String toString() {
        return ERROR_MESSAGE;
    }
}

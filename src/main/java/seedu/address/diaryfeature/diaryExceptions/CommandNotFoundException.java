package seedu.address.diaryfeature.diaryexceptions;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String message) {
        super(message);
    }
}

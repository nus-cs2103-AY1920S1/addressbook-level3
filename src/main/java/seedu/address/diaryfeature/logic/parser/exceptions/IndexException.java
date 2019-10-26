package seedu.address.diaryfeature.logic.parser.exceptions;

public class IndexException extends Exception {
    String message;
    public IndexException(String input) {
        super();
        message = input;
    }

    public String toString() {
        return message;
    }
}

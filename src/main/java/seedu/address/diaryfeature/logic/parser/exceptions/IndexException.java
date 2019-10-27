package seedu.address.diaryfeature.logic.parser.exceptions;

/**
 *
 */
public class IndexException extends Exception {
    private String message;
    public IndexException(String input) {
        super();
        message = input;
    }

    /**
     *
     * @return message
     */
    public String toString() {
        return message;
    }
}

package seedu.address.ui.exception;

/**
 * Represents an error which occurs during execution of a UiChange.
 */
public class EnumNotPresentException extends RuntimeException {

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message}.
     */
    public EnumNotPresentException(String message) {
        super(message);
    }
}

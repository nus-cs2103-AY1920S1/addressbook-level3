package seedu.weme.logic.prompter.exceptions;

import seedu.weme.commons.exceptions.IllegalValueException;

/**
 * Represents a prompt error encountered by a prompter.
 */
public class PromptException extends IllegalValueException {

    public PromptException(String message) {
        super(message);
    }

    public PromptException(String message, Throwable cause) {
        super(message, cause);
    }
}

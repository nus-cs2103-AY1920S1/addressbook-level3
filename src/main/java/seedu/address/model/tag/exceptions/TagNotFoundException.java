package seedu.address.model.tag.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class TagNotFoundException extends CommandException {
    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException() {
        super("Tag not found");
    }
}

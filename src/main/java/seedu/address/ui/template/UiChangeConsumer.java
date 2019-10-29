package seedu.address.ui.template;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents an operation that accepts a single Command Word and returns no result.
 */
@FunctionalInterface
public interface UiChangeConsumer {
    public void changeUi (String commandWord) throws CommandException;
}

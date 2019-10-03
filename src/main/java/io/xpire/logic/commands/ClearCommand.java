package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.model.ExpiryDateTracker;
import io.xpire.model.Model;

/**
 * Clears all items in the list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Item list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setExpiryDateTracker(new ExpiryDateTracker());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

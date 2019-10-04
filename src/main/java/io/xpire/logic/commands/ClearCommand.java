package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.model.Xpire;
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
        model.setXpire(new Xpire());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

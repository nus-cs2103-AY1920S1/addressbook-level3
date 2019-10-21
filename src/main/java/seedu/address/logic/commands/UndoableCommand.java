package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

/**
 * Superclass of all commands that can be undone
 * */

public abstract class UndoableCommand extends Command {
    public abstract void reverse(ItemModel model) throws CommandException;
}

package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Superclass of all commands that can be undone
 * */

public abstract class UndoableCommand extends Command {
    public abstract void reverse(ItemModel model) throws CommandException;
    public abstract String getCommandWord();
}

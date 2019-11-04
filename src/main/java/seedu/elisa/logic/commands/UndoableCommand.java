package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Superclass of all commands that can be undone
 * */

public abstract class UndoableCommand extends Command {
    private boolean isExecuted = false;
    public abstract void reverse(ItemModel model) throws CommandException;
    public abstract String getCommandWord();

    public boolean isExecuted() {
        return isExecuted;
    }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }
}

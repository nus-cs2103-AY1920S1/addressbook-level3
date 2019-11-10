package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.State;
import io.xpire.model.state.StateManager;

/**
 * Undo the previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_SHORTHAND = "u";
    public static final String MESSAGE_UNDO_SUCCESS = "Undo %s\nUser Input: %s";
    public static final String MESSAGE_UNDO_FAILURE = "There are no previous commands to undo.";

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        if (stateManager.isNotUndoable()) {
            throw new CommandException(MESSAGE_UNDO_FAILURE);
        }
        State previousState = stateManager.undo(new ModifiedState(model));
        model.update(previousState);
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }


    @Override
    public String toString() {
        return "Undo Command";
    }
}

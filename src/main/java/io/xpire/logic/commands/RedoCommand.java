package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.state.State;
import io.xpire.model.state.StateManager;

/**
 * Redo the previous Undo Command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_SHORTHAND = "r";
    public static final String MESSAGE_REDO_SUCCESS = "Redo %s\nUser Input: %s";
    public static final String MESSAGE_REDO_FAILURE = "There are no commands to redo.";

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        if (stateManager.isNotRedoable()) {
            throw new CommandException(MESSAGE_REDO_FAILURE);
        }
        State succeedingState = stateManager.redo();
        model.update(succeedingState);
        return new CommandResult(MESSAGE_REDO_SUCCESS);
    }

    @Override
    public String toString() {
        return "Redo Command";
    }
}

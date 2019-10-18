package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_REDO_SUCCESS;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.undo.UndoRedoManager;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class RedoCommand extends Command {

    private final UndoRedoManager manager;

    RedoCommand(RedoCommandBuilder builder) {
        this.manager = builder.getManager();
    }

    public static CommandBuilder newBuilder(UndoRedoManager manager) {
        return new RedoCommandBuilder(manager).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        manager.redo();
        return new UserOutput(MESSAGE_REDO_SUCCESS);
    }
}

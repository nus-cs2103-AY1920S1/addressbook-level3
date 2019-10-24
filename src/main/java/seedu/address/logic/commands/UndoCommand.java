package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_UNDO_SUCCESS;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.undo.UndoRedoManager;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class UndoCommand extends Command {

    private final UndoRedoManager manager;

    UndoCommand(UndoCommandBuilder builder) {
        this.manager = builder.getManager();
    }

    public static CommandBuilder newBuilder(UndoRedoManager manager) {
        return new UndoCommandBuilder(manager).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        manager.undo();
        return new UserOutput(MESSAGE_UNDO_SUCCESS);
    }
}

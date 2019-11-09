package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_UNDO_SUCCESS;

import seedu.address.logic.UndoRedoManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.UserOutput;

//@@author bruceskellator

/**
 * Represents a Command which undoes the previous command.
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

package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_REDO_SUCCESS;

import seedu.address.logic.UndoRedoManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.UserOutput;

//@@author bruceskellator

/**
 * Represents a Command which redoes the previously undone command.
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

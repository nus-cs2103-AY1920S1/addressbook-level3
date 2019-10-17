package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_UNDO_SUCCESS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class UndoCommand extends Command {

    private final Model model;

    UndoCommand(UndoCommandBuilder builder) {
        this.model = builder.getModel();
    }

    public static CommandBuilder newBuilder(Model model) {
        return new UndoCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        model.undoFromHistory();
        return new UserOutput(MESSAGE_UNDO_SUCCESS);
    }
}

package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_REDO_SUCCESS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class RedoCommand extends Command {

    private final Model model;

    RedoCommand(RedoCommandBuilder builder) {
        this.model = builder.getModel();
    }

    public static CommandBuilder newBuilder(Model model) {
        return new RedoCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        model.redoFromHistory();
        return new UserOutput(MESSAGE_REDO_SUCCESS);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes an action on the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Successfully undid: %1$s";
    public static final String MESSAGE_NOTHING_TO_UNDO = "There is nothing to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasUndo()) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, model.undo()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof UndoCommand; // instanceof handles nulls
    }
}

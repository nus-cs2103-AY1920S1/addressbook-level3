package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Undoes an action on the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "-undo";

    public static final String MESSAGE_SUCCESS = "Successfully undid: %1$s";
    public static final String MESSAGE_NOTHING_TO_UNDO = "There is nothing to undo";

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
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

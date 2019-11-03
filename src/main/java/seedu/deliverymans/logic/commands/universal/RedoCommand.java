package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Redoes an action on the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "-redo";

    public static final String MESSAGE_SUCCESS = "Successfully redid: %1$s";
    public static final String MESSAGE_NOTHING_TO_REDO = "There is nothing to redo";

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);

        if (!model.hasRedo()) {
            throw new CommandException(MESSAGE_NOTHING_TO_REDO);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, model.redo()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof RedoCommand; // instanceof handles nulls
    }
}

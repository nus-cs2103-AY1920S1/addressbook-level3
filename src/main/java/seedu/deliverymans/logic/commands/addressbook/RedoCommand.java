package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes an action on the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Successfully redid: %1$s";
    public static final String MESSAGE_NOTHING_TO_REDO = "There is nothing to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
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

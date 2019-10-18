package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the state of the address book to a previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "An undo has been performed!";
    public static final String UNDO_NOT_POSSIBLE = "Nothing to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(UNDO_NOT_POSSIBLE);
        }

        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

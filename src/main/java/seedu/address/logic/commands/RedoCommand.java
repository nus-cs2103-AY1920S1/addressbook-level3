package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redos the state of the address book to a previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "A redo has been performed!";
    public static final String REDO_NOT_POSSIBLE = "Nothing to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(REDO_NOT_POSSIBLE);
        }

        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
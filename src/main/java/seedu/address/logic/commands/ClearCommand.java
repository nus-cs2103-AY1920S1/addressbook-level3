package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.core.CommandResult;
import seedu.address.logic.commands.core.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_UNDO_CLEAR_SUCCESS = "Undo successful! Address book has been restored!";
    public static final String MESSAGE_UNDO_CLEAR_ERROR = "Changes to the address book cannot be undone!";

    private ReadOnlyAddressBook originalModel;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        originalModel = new AddressBook(model.getAddressBook());
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (originalModel == null) {
            throw new CommandException(MESSAGE_UNDO_CLEAR_ERROR);
        }

        model.setAddressBook(originalModel);
        return new CommandResult(MESSAGE_UNDO_CLEAR_SUCCESS);
    }
}

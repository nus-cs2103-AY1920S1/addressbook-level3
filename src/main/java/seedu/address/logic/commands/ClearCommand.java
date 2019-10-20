package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mortago has been cleared!";
    public static final String MESSAGE_UNDO_SUCCESS = "Mortago has been restored!";

    private ReadOnlyAddressBook savedModel;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        savedModel = new AddressBook(model.getAddressBook());
        model.setAddressBook(new AddressBook());
        UniqueIdentificationNumberMaps.clearAllEntries();

        setUndoable();
        model.addExecutedCommand(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        model.setAddressBook((ReadOnlyAddressBook) savedModel);
        setRedoable();
        model.addUndoneCommand(this);
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}

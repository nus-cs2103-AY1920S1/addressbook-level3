package seedu.address.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.address.model.AddressBook;
import seedu.address.address.model.AddressBookModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command<AddressBookModel> {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(AddressBookModel addressBookModel) {
        requireNonNull(addressBookModel);
        addressBookModel.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

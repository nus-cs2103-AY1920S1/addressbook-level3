package seedu.jarvis.logic.commands.address;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.model.AddressBook;
import seedu.jarvis.model.AddressModel;

/**
 * Clears the address book.
 */
public class ClearAddressCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(AddressModel addressModel) {
        requireNonNull(addressModel);
        addressModel.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

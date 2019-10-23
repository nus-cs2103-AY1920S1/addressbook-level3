package seedu.address.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.address.model.AddressBookModel.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.address.model.AddressBookModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command<AddressBookModel> {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(AddressBookModel addressBookModel) {
        requireNonNull(addressBookModel);
        addressBookModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

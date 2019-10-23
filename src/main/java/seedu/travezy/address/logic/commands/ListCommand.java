package seedu.travezy.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.travezy.address.model.AddressBookModel.PREDICATE_SHOW_ALL_PERSONS;

import seedu.travezy.address.model.AddressBookModel;
import seedu.travezy.logic.commands.CommandResult;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(AddressBookModel addressBookModel) {
        requireNonNull(addressBookModel);
        addressBookModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

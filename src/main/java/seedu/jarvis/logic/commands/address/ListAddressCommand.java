package seedu.jarvis.logic.commands.address;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.AddressModel.PREDICATE_SHOW_ALL_PERSONS;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.model.AddressModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListAddressCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(AddressModel addressModel) {
        requireNonNull(addressModel);
        addressModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

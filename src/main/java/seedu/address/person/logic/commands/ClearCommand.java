package seedu.address.person.logic.commands;

import seedu.address.person.model.AddressBook;
import seedu.address.person.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, seedu.address.transaction.logic.Logic transactionLogic) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

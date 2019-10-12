package seedu.address.logic.calendar.commands;

import seedu.address.model.calendar.AddressCalendarBook;
import seedu.address.model.calendar.Model;

import static java.util.Objects.requireNonNull;


/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressCalendarBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

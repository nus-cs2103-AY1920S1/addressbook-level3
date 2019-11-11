package unrealunity.visit.logic.commands;

import static java.util.Objects.requireNonNull;

import unrealunity.visit.model.AddressBook;
import unrealunity.visit.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.resetAppointments();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

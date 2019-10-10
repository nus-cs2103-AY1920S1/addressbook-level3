package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import tagline.logic.commands.Command;
import tagline.logic.commands.CommandResult;
import tagline.model.AddressBook;
import tagline.model.Model;

/**
 * Clears the address book.
 */
public class ClearContactCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

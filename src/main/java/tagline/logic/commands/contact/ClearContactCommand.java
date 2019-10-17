package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.contact.AddressBook;

/**
 * Clears the address book.
 */
public class ClearContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Contacts have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, ViewType.CONTACT);
    }
}

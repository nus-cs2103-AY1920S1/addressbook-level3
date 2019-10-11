package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static tagline.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import tagline.logic.commands.CommandResult;
import tagline.model.Model;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

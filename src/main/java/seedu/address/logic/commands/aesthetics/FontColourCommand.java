package seedu.address.logic.commands.aesthetics;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Edits the details of an existing user in the address book.
 */
public class FontColourCommand extends Command {

    public static final String COMMAND_WORD = "fontcolour";
    public static final String MESSAGE_SUCCESS = "Font colour has been set!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

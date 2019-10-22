package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Clears the phone book.
 */
public class ClearPhoneCommand extends Command {

    public static final String COMMAND_WORD = "clear-p";
    public static final String MESSAGE_SUCCESS = "Phone book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPhoneBook(new DataBook<Phone>());
        return new CommandResult(MESSAGE_SUCCESS, UiChange.PHONE);
    }
}

package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Finance log has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new FinanceLog());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

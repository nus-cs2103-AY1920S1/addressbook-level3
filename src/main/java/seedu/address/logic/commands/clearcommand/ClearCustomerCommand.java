package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Clears the customer book.
 */
public class ClearCustomerCommand extends Command {

    public static final String COMMAND_WORD = "clear-c";
    public static final String MESSAGE_SUCCESS = "Customer book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCustomerBook(new DataBook<Customer>());
        return new CommandResult(MESSAGE_SUCCESS, UiChange.CUSTOMER);
    }
}

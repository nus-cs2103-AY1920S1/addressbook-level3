package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Clears the order book.
 */
public class ClearOrderCommand extends Command {

    public static final String COMMAND_WORD = "clear-o";
    public static final String MESSAGE_SUCCESS = "Order book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setOrderBook(new DataBook<Order>());
        return new CommandResult(MESSAGE_SUCCESS, UiChange.CUSTOMER);
    }
}

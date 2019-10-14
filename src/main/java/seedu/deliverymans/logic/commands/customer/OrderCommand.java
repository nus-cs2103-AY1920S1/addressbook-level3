package seedu.deliverymans.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_DELIVERYMAN;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.order.Order;

/**
 * Order command
 */
public class OrderCommand extends Command {
    public static final String COMMAND_WORD = "order";

    public static final String MESSAGE_ADD_SUCCESS = "Added order: %1$s";

    public static final String MESSAGE_ADD_ORDER_USAGE = COMMAND_WORD
            + ": Adds an order to the manager. "
            + "Parameters: "
            + PREFIX_ORDER + "ORDER "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER]\n"
            + "[" + PREFIX_RESTAURANT + "RESTAURANT]\n"
            + "[" + PREFIX_DELIVERYMAN + "DELIVERYMAN]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "Sam "
            + PREFIX_RESTAURANT + "KFC "
            + PREFIX_DELIVERYMAN + "Deliveryman #1337";

    private final Order toAdd;

    public OrderCommand(Order toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.addOrder(toAdd);
        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderCommand // instanceof handles nulls
                && toAdd.equals(((OrderCommand) other).toAdd));
    }
}

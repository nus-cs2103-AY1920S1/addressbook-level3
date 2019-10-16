package seedu.deliverymans.logic.commands.universal;

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
public class AddOrderCommand extends Command {
    public static final String COMMAND_WORD = "order";

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

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This order already exists";

    private final Order toAdd;

    public AddOrderCommand(Order toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasOrder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.addOrder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }
}

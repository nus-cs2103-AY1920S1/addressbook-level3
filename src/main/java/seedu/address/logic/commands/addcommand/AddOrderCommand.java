package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds an order to SML.
 */
public class AddOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add-o";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order to SML. "
            + "Parameters: "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_PHONE + "2 "
            + PREFIX_PRICE + "$1000 "
            + PREFIX_TAG + "Urgent ";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in SML.";

    private final Index toAddCustomerIndex;
    private final Index toAddPhoneIndex;
    private final Price toAddPrice;
    private final Set<Tag> toAddTags;

    /**
     * Creates an AddCommand to add the specified {@code Order}
     */
    public AddOrderCommand(Index customerIndex, Index phoneIndex, Price price, Set<Tag> tags) {
        requireNonNull(customerIndex);
        requireNonNull(phoneIndex);
        requireNonNull(price);
        requireNonNull(tags);

        this.toAddCustomerIndex = customerIndex;
        this.toAddPhoneIndex = phoneIndex;
        this.toAddPrice = price;
        this.toAddTags = tags;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);

        List<Customer> lastShownCustomerList = model.getFilteredCustomerList();

        if (toAddCustomerIndex.getZeroBased() >= lastShownCustomerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customer = lastShownCustomerList.get(toAddCustomerIndex.getZeroBased());

        List<Phone> lastShownPhoneList = model.getFilteredPhoneList();

        if (toAddPhoneIndex.getZeroBased() >= lastShownPhoneList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
        }

        Phone phone = lastShownPhoneList.get(toAddPhoneIndex.getZeroBased());

        Order toAdd = new Order(UUID.randomUUID(), customer, phone, toAddPrice,
                Status.UNSCHEDULED, Optional.empty(), toAddTags);

        if (model.hasOrder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.addOrder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), UiChange.ORDER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAddCustomerIndex.equals(((AddOrderCommand) other).toAddCustomerIndex)
                && toAddPhoneIndex.equals(((AddOrderCommand) other).toAddPhoneIndex)
                && toAddPrice.equals(((AddOrderCommand) other).toAddPrice)
                && toAddTags.equals(((AddOrderCommand) other).toAddTags));
    }
}

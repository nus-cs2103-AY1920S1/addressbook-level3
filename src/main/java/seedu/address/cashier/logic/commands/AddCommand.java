package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_ADDED_ITEM;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_FOR_SALE_CASHIER;

import java.util.logging.Logger;

import seedu.address.cashier.logic.commands.exception.InsufficientAmountException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.inventory.model.Item;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.util.CommandResult;

/**
 * Adds an item to the sales list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private final String description;
    private final int quantity;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add an item
     * @param description of the item in the inventory list to add
     * @param quantity of the item to be sold
     */
    public AddCommand(String description, int quantity) {
        assert description != null : "Description cannot be null.";
        assert quantity > 0 : "Quantity must be a positive integer.";

        logger.info("description of item added: " + description);
        logger.info("quantity of item added: " + quantity);

        this.description = description;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(Model modelManager,
                                 seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws NoSuchItemException, InsufficientAmountException {

        Item i;
        // if the item with the specified description is not available for sale
        if (!modelManager.isSellable(description)) {
            throw new NoSuchItemException(NO_SUCH_ITEM_FOR_SALE_CASHIER);
        }
        if (!modelManager.hasSufficientQuantityToAdd(description, quantity)) {
            int quantityLeft = modelManager.getStockLeft(description);
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_STOCK, quantityLeft, description));
        }

        try {
            i = modelManager.addItem(description, quantity);
        } catch (NoSuchItemException e) {
            throw new NoSuchItemException(NO_SUCH_ITEM_CASHIER);
        }
        logger.info("Item added: " + i.toString());
        return new CommandResult(String.format(MESSAGE_ADDED_ITEM, quantity, i.getDescription()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && description.equals(((AddCommand) other).description)
                && quantity == ((AddCommand) other).quantity);
    }

}



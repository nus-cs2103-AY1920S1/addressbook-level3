package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_EDIT_SUCCESS;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;

import java.util.logging.Logger;

import seedu.address.cashier.logic.commands.exception.InsufficientAmountException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.util.CommandResult;

/**
 * Edits the details of an existing item in the sales list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    private final int index;
    private final int quantity;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an EditCommand to add an item
     * @param index of the item in the sales list to edit
     * @param quantity of the edited item
     */
    public EditCommand(int index, int quantity) {
        assert index > 0 : "Index must be a positive integer.";
        assert quantity > 0 : "Quantity cannot be negative.";

        logger.info("index of item edited: " + index);
        logger.info("quantity of item edited: " + quantity);

        this.index = index;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(seedu.address.cashier.model.Model model,
                                 seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws Exception {
        Item i;
        try {
            i = model.findItemByIndex(index);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchIndexException(CashierMessages.NO_SUCH_INDEX_CASHIER);
        }
        if (!model.hasSufficientQuantityToEdit(index, quantity)) {
            String description = model.findItemByIndex(index).getDescription();
            int quantityLeft = model.getStockLeft(description);
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_STOCK, quantityLeft, description));
        }
        i = model.editItem(index, quantity);
        logger.info("Edited item: " + i.toString());
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, i.getDescription(), i.getQuantity()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && index == (((EditCommand) other).index)
                && quantity == ((EditCommand) other).quantity);
    }
}

package seedu.address.cashier.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_EDIT_SUCCESS;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;

import seedu.address.cashier.logic.exception.InsufficientAmountException;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.Model;

/**
 * Edits the details of an existing item in the sales list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    private static int index;
    private static int quantity;

    /**
     * Creates an EditCommand to add an item
     * @param index of the item in the sales list to edit
     * @param quantity of the edited item
     */
    public EditCommand(int index, int quantity) {
        assert index > 0 : "Index must be a positive integer.";
        assert quantity >= 0 : "Quantity cannot be negative.";
        this.index = index;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(seedu.address.cashier.model.Model model, Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel) throws Exception {
        Item i;
        try {
            i = model.findItemByIndex(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(CashierMessages.NO_SUCH_INDEX_CASHIER);
        }
        if (!model.hasSufficientQuantityToEdit(index, quantity)) {
            String description = model.findItemByIndex(index).getDescription();
            int quantityLeft = model.getStockLeft(description);
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_STOCK, quantityLeft, description));
        }
        i = model.editItem(index, quantity);
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, i.getDescription(), i.getQuantity()));
    }
}

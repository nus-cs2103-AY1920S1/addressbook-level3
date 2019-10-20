package seedu.address.cashier.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_DELETED_ITEM;

import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.Model;

/**
 * Deletes an item identified using its displayed index from the sales list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private int index;
    private seedu.address.cashier.model.Model modelManager;

    /**
     * Creates a DeleteCommand to delete an item
     * @param index of the item in the sales list to be deleted
     */
    public DeleteCommand(int index) {
        assert index >= 0 : "Index must be a positive integer.";
        this.index = index;
    }

    @Override
    public CommandResult execute(seedu.address.cashier.model.Model modelManager, Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel)
            throws NoSuchIndexException, ParseException {
        Item item;
        try {
            item = modelManager.findItemByIndex(index);
            modelManager.deleteItem(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(CashierMessages.NO_SUCH_INDEX_CASHIER);
        }

        return new CommandResult(String.format(MESSAGE_DELETED_ITEM, item));
    }
}

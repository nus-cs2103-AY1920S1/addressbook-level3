package seedu.address.cashier.commands;

import seedu.address.cashier.model.ModelManager;
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
        this.index = index;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(ModelManager model, Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel) throws Exception {
        Item i = model.editItem(index, quantity);
        return new CommandResult(CashierMessages.editSuccessfully(i));
    }
}

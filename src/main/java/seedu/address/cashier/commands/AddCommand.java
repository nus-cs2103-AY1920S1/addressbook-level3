package seedu.address.cashier.commands;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchItemException;

import seedu.address.cashier.ui.CashierMessages;

import seedu.address.inventory.model.Item;


/**
 * Adds an item to the sales list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private String description;
    private final int quantity;

    /**
     * Creates an AddCommand to add an item
     * @param description of the item in the inventory list to add
     * @param quantity of the item to be sold
     */
    public AddCommand(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, seedu.address.person.model.Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel)
            throws NoSuchItemException {
        CashierMessages cashierMessages = new CashierMessages();
        Item i = modelManager.addItem(description, quantity);
        return new CommandResult(cashierMessages.addedItem(i));
    }
}



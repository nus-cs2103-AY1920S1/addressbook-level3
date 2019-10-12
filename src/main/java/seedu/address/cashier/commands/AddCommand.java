package seedu.address.cashier.commands;


import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchItemException;

public class AddCommand extends Command {

    private final String description;
    private final int quantity;
    public static final String COMMAND_WORD = "add";

    public AddCommand(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, seedu.address.person.model.Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel)
            throws NoSuchItemException {
        CashierUi cashierUi = new CashierUi();
        Item i = modelManager.addItem(description, quantity);
        return new CommandResult(cashierUi.addedItem(i));
    }
}



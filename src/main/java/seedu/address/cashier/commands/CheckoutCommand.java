package seedu.address.cashier.commands;

import java.util.logging.Logger;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;

/**
 * Checkout a list of item to be sold.
 */
public class CheckoutCommand extends Command {

    public static final String COMMAND_WORD = "checkout";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final double totalAmount;
    private final double change;

    /**
     * Creates a CheckoutCommand to update the inventory and the transaction.
     * @param totalAmount of the sales list
     * @param change to be returned to the customer
     */
    public CheckoutCommand(double totalAmount, double change) {
        this.totalAmount = totalAmount;
        this.change = change;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, seedu.address.person.model.Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel)
            throws Exception {
        CashierMessages cashierMessages = new CashierMessages();
        Person p = modelManager.getCashier();
        modelManager.checkoutAsTransaction(totalAmount, p, transactionModel);
        logger.info(p.toString());
        modelManager.updateInventoryList();
        modelManager.writeInInventoryFile();
        inventoryModel.readInUpdatedList();
        ClearCommand clearCommand = new ClearCommand();
        clearCommand.execute(modelManager, personModel, transactionModel, inventoryModel);
        return new CommandResult(cashierMessages.checkoutSuccessful(String.valueOf(totalAmount),
                String.valueOf(change)));
    }
}

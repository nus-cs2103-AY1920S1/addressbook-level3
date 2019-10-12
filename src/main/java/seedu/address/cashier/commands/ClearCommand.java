package seedu.address.cashier.commands;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.Model;

/**
 * Clears the sales list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    /**
     * Creates a ClearCommand to clear all the sales items.
     */
    public ClearCommand() {
    }

    @Override
    public CommandResult execute(ModelManager model, Model personModel,
                                        seedu.address.transaction.model.Model transactionModel,
                                        seedu.address.inventory.model.Model inventoryModel) throws Exception {
        model.clearSalesList();
        return new CommandResult(CashierMessages.CLEARED_SUCCESSFULLY);
    }
}

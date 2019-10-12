package seedu.address.cashier.commands;

import java.util.logging.Logger;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;

public class CheckoutCommand extends Command {

    public static final String COMMAND_WORD = "checkout";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final double totalAmount;
    private final double change;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public CheckoutCommand(double totalAmount, double change) {
        this.totalAmount = totalAmount;
        this.change = change;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, seedu.address.person.model.Model personModel)
            throws Exception {
        CashierUi cashierUi = new CashierUi();
        Person p = modelManager.getCashier();
        modelManager.checkoutAsTransaction(totalAmount, p);
        logger.info(p.toString());
        modelManager.updateInventoryList();
        modelManager.writeInInventoryFile();
        return new CommandResult(cashierUi.checkoutSuccessful(String.valueOf(totalAmount), String.valueOf(change)));
    }
}

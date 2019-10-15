package seedu.address.cashier.commands;

import java.util.logging.Logger;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.exception.NoSuchPersonException;

/**
 * Sets a cashier who is in-charge of the sales made.
 */
public class SetCashierCommand extends Command {

    public static final String COMMAND_WORD = "cashier";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Person cashier;


    /**
     * Creates a SetCashierCommand to add the specified {@code Person}
     */
    public SetCashierCommand(Person p) {
        cashier = p;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException {
        CashierMessages cashierMessages = new CashierMessages();
        modelManager.setCashier(cashier);
        return new CommandResult(cashierMessages.addCashierSuccessful(cashier));
    }

}

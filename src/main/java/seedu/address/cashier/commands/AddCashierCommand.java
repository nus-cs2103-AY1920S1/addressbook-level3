package seedu.address.cashier.commands;

import java.util.logging.Logger;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;
import seedu.address.transaction.model.exception.NoSuchPersonException;

public class AddCashierCommand extends Command {

    private String cashierName;
    public static final String COMMAND_WORD = "cashier";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCashierCommand(String name) {
        cashierName = name;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, Model personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException {
        CashierUi cashierUi = new CashierUi();
        modelManager.setCashier(cashierName, personModel);
        logger.info(cashierName);
        return new CommandResult(CashierUi.addCashierSuccessful(cashierName));
    }
}

package seedu.address.cashier.commands;

import java.util.logging.Logger;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.exception.NoSuchPersonException;

public class AddCashierCommand extends Command {

    private Person cashier;
    public static final String COMMAND_WORD = "cashier";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCashierCommand(Person p) {
        System.out.println(p.getName());
        cashier = p;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, Model personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException {
        CashierUi cashierUi = new CashierUi();
        System.out.println("before logger");
        modelManager.setCashier(cashier, personModel);
        //logger.info(cashierName);
        System.out.println("after logger");
        return new CommandResult(CashierUi.addCashierSuccessful(cashier));
    }
}

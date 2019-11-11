package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_ADD_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_PERSON;

import java.util.logging.Logger;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.util.CommandResult;

/**
 * Sets a cashier who is in-charge of the sales made.
 */
public class SetCashierCommand extends Command {

    public static final String COMMAND_WORD = "cashier";
    private final Person cashier;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a SetCashierCommand to add the specified {@code Person}
     */
    public SetCashierCommand(Person p) {
        assert p != null : "Cashier cannot be null.";
        logger.info("Person set as Cashier: " + p.getName().toString());
        cashier = p;
    }

    @Override
    public CommandResult execute(seedu.address.cashier.model.Model modelManager,
                                 seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws NoSuchIndexException, CommandException,
            seedu.address.cashier.logic.commands.exception.NoSuchPersonException, NoSuchPersonException {

        if (!personModel.hasPerson(cashier)) {
            throw new NoSuchPersonException(NO_SUCH_PERSON);
        }
        modelManager.setCashier(cashier);
        logger.info("Cashier: " + cashier.getName().toString());
        return new CommandResult(String.format(MESSAGE_ADD_CASHIER, cashier.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetCashierCommand // instanceof handles nulls
                && cashier.equals(((SetCashierCommand) other).cashier));
    }

}

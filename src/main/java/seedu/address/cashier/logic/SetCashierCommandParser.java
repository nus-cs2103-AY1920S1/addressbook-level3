package seedu.address.cashier.logic;

import seedu.address.cashier.commands.SetCashierCommand;
import seedu.address.cashier.logic.exception.NoSuchPersonException;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;

/**
 * Parses input arguments and creates a new SetCashierCommand object.
 */
public class SetCashierCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCashierCommand
     * and returns an SetCashierCommand object for execution.
     * @param name of the cashier to be passed in
     * @param modelManager which the command operates on
     * @param personModel which the command uses to find the cashier
     * @throws NoSuchPersonException if the user input is an invalid person
     * @throws ParseException if the user input does not conform the expected format
     */
    public static SetCashierCommand parse(String name, ModelManager modelManager, Model personModel)
            throws NoSuchPersonException, ParseException {
        try {
            Person cashier = personModel.getPersonByName(name.trim());
            return new SetCashierCommand(cashier);
        } catch (PersonNotFoundException e) {
            throw new NoSuchPersonException(CashierMessages.NO_SUCH_PERSON);
        } catch (Exception e) {
            throw new ParseException(CashierMessages.MESSAGE_INVALID_CASHIERCOMMAND_FORMAT);
        }
    }

}

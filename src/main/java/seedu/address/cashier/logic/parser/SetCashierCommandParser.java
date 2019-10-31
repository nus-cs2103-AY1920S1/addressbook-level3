package seedu.address.cashier.logic.parser;

import java.util.logging.Logger;

import seedu.address.cashier.logic.commands.SetCashierCommand;
import seedu.address.cashier.logic.commands.exception.NoSuchPersonException;
import seedu.address.cashier.logic.parser.exception.ParseException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;

/**
 * Parses input arguments and creates a new SetCashierCommand object.
 */
public class SetCashierCommandParser implements Parser {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the SetCashierCommand
     * and returns an SetCashierCommand object for execution.
     * @param name of the cashier to be passed in
     * @param modelManager which the command operates on
     * @param personModel which the command uses to find the cashier
     * @throws NoSuchPersonException if the user input is an invalid person
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCashierCommand parse(String name,
                                          seedu.address.cashier.model.Model modelManager, Model personModel)
            throws NoSuchPersonException, ParseException {
        try {
            Person cashier = personModel.getPersonByName(name.trim());
            return new SetCashierCommand(cashier);
        } catch (PersonNotFoundException e) {
            logger.info("There is no such person in the person model.");
            throw new NoSuchPersonException(CashierMessages.NO_SUCH_PERSON);
        }
    }

}



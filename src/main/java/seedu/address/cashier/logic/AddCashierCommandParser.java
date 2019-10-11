package seedu.address.cashier.logic;

import seedu.address.cashier.commands.AddCashierCommand;
import seedu.address.cashier.logic.exception.NoSuchPersonException;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;

public class AddCashierCommandParser {

    public static AddCashierCommand parse(String name, ModelManager modelManager, Model personModel)
            throws NoSuchPersonException, ParseException {
        try {
            System.out.println(name);
            Person cashier = personModel.getPersonByName(name);
            System.out.println("after add cashier");
            return new AddCashierCommand(cashier);
        } catch (PersonNotFoundException e) {
            System.out.println("invalid cashier name command");
            throw new NoSuchPersonException(CashierUi.NO_SUCH_PERSON);
        } catch (Exception e) {
            throw new ParseException(CashierUi.MESSAGE_INVALID_CASHIERCOMMAND_FORMAT);
        }
    }

}

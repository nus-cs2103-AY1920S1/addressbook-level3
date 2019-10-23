package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_INDEX_CASHIER;

import seedu.address.cashier.logic.commands.DeleteCommand;
import seedu.address.cashier.logic.commands.exception.NotANumberException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws NotANumberException if the user input is not a number
     */
    public static DeleteCommand parse(String userInput, Model model, seedu.address.person.model.Model personModel)
            throws NotANumberException, NoSuchIndexException {
        int index;
        try {
            index = Integer.parseInt(userInput.substring(1));
        } catch (Exception e) {
            throw new NotANumberException(CashierMessages.INDEX_NOT_A_NUMBER);
        }

        if (index <= 0) {
            throw new NoSuchIndexException(NO_SUCH_INDEX_CASHIER);
        }

        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }
}

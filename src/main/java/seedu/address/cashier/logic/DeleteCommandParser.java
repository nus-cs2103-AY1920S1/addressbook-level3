package seedu.address.cashier.logic;

import seedu.address.cashier.commands.DeleteCommand;
import seedu.address.cashier.logic.exception.NotANumberException;
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
    public static DeleteCommand parse(String userInput) throws NotANumberException {
        int index;
        try {
            index = Integer.parseInt(userInput.substring(1));
        } catch (Exception e) {
            throw new NotANumberException(CashierMessages.INDEX_NOT_A_NUMBER);
        }

        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }
}

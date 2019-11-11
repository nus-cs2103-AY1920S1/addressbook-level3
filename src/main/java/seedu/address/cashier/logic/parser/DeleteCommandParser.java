package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_INDEX_CASHIER;

import java.util.logging.Logger;

import seedu.address.cashier.logic.commands.DeleteCommand;
import seedu.address.cashier.logic.commands.exception.NotANumberException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.commons.core.LogsCenter;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws NotANumberException if the user input is not a number
     */
    public DeleteCommand parse(String userInput, Model model,
                               seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws NotANumberException, NoSuchIndexException {
        int index;
        try {
            String[] input = userInput.split(" ");
            index = Integer.parseInt(input[1]);
        } catch (Exception e) {
            logger.info("Index is not an integer.");
            throw new NotANumberException(CashierMessages.INDEX_NOT_A_NUMBER);
        }

        if (index <= 0) {
            throw new NoSuchIndexException(NO_SUCH_INDEX_CASHIER);
        }

        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }
}

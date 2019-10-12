package seedu.address.overview.logic;

import seedu.address.overview.commands.DeleteCommand;
import seedu.address.overview.logic.exception.NotANumberException;
import seedu.address.overview.ui.TransactionMessages;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class NotifyCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws NotANumberException if the user input does not conform the expected format
     */
    public static DeleteCommand parse(String userInput) throws NotANumberException {
        int index;
        try {
            index = Integer.parseInt(userInput.substring(1));

        } catch (Exception e) {
            throw new NotANumberException(TransactionMessages.MESSAGE_NOT_A_NUMBER);
        }

        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }
}

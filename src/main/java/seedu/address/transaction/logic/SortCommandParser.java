package seedu.address.transaction.logic;

import seedu.address.transaction.commands.SortAmountCommand;
import seedu.address.transaction.commands.SortCommand;
import seedu.address.transaction.commands.SortDateCommand;
import seedu.address.transaction.commands.SortNameCommand;
import seedu.address.transaction.commands.SortResetCommand;
import seedu.address.transaction.logic.exception.NoSuchSortException;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws NoSuchSortException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws NoSuchSortException {
        String[] argsArr = args.split(" ");
        if (argsArr[1].equals("date")) {
            return new SortDateCommand();
        } else if (argsArr[1].equals("name")) {
            return new SortNameCommand();
        } else if (argsArr[1].equals("amount")) {
            return new SortAmountCommand();
        } else if (argsArr[1].equals("reset")) {
            return new SortResetCommand();
        } else {
            throw new NoSuchSortException(TransactionMessages.MESSAGE_NO_SUCH_SORT_COMMAND);
        }
    }
}

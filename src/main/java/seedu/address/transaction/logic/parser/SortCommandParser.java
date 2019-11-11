package seedu.address.transaction.logic.parser;

import seedu.address.transaction.logic.commands.SortAmountCommand;
import seedu.address.transaction.logic.commands.SortCommand;
import seedu.address.transaction.logic.commands.SortDateCommand;
import seedu.address.transaction.logic.commands.SortNameCommand;
import seedu.address.transaction.logic.commands.SortResetCommand;
import seedu.address.transaction.logic.commands.exception.NoSuchSortException;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements IndependentCommandParser {
    public static final String COMMAND_WORD_SORT_BY_DATE = "date";
    public static final String COMMAND_WORD_SORT_BY_AMOUNT = "amount";
    public static final String COMMAND_WORD_SORT_BY_NAME = "name";
    public static final String COMMAND_WORD_SORT_BY_RESET = "reset";


    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws NoSuchSortException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws NoSuchSortException {
        String argsArr = args.trim();
        if (argsArr.equals(COMMAND_WORD_SORT_BY_DATE)) {
            return new SortDateCommand();
        } else if (argsArr.equals(COMMAND_WORD_SORT_BY_NAME)) {
            return new SortNameCommand();
        } else if (argsArr.equals(COMMAND_WORD_SORT_BY_AMOUNT)) {
            return new SortAmountCommand();
        } else if (argsArr.equals(COMMAND_WORD_SORT_BY_RESET)) {
            return new SortResetCommand();
        } else {
            throw new NoSuchSortException(TransactionMessages.MESSAGE_NO_SUCH_SORT_COMMAND);
        }
    }
}

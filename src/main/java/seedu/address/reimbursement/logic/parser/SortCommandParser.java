package seedu.address.reimbursement.logic.parser;

import seedu.address.reimbursement.logic.commands.SortAmountCommand;
import seedu.address.reimbursement.logic.commands.SortCommand;
import seedu.address.reimbursement.logic.commands.SortDeadlineCommand;
import seedu.address.reimbursement.logic.commands.SortNameCommand;
import seedu.address.reimbursement.logic.parser.exception.ParseException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Parser for sort command.
 */
public class SortCommandParser implements IndependentCommandParser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format
     */
    public SortCommand parse(String arguments) throws ParseException {
        String[] argsArr = arguments.split(" ");
        if (argsArr.length != 2) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_SORT_COMMAND_FORMAT);
        } else {
            assert argsArr.length == 2 : "sort command is invalid";
            if (argsArr[1].equals("date")) {
                return new SortDeadlineCommand();
            } else if (argsArr[1].equals("name")) {
                return new SortNameCommand();
            } else if (argsArr[1].equals("amount")) {
                return new SortAmountCommand();
            } else {
                throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_SORT_COMMAND_FORMAT);
            }
        }

    }
}

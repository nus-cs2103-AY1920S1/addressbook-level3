package seedu.address.reimbursement.logic.parser;

import seedu.address.reimbursement.commands.Command;
import seedu.address.reimbursement.commands.SortAmountCommand;
import seedu.address.reimbursement.commands.SortDeadlineCommand;
import seedu.address.reimbursement.commands.SortNameCommand;
import seedu.address.reimbursement.logic.exception.ParseException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 *Parser for sort command.
 */
public class SortCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException If the user input does not conform the expected format
     */
    public Command parse(String arguments) throws ParseException {
        String[] argsArr = arguments.split(" ");
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

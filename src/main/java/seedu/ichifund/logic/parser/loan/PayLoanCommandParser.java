package seedu.ichifund.logic.parser.loan;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.loan.PayLoanCommand;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PayLoanCommand object
 */
public class PayLoanCommandParser implements Parser<PayLoanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PayLoanCommand
     * and returns a PayLoanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PayLoanCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PayLoanCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayLoanCommand.MESSAGE_USAGE), pe);
        }
    }

}

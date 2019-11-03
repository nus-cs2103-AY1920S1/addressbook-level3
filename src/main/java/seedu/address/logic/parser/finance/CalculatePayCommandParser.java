package seedu.address.logic.parser.finance;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.finance.PaySlip;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new CalculatePayCommand object
 */
public class CalculatePayCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the CalculatePayCommand
     * and returns a CalculatePayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PaySlip parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PaySlip(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaySlip.MESSAGE_USAGE), pe);
        }
    }
}

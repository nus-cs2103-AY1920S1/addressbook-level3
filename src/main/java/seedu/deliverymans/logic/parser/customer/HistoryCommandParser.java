package seedu.deliverymans.logic.parser.customer;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.customer.CustomerHistoryCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HistoryCommand object
 */
public class HistoryCommandParser implements Parser<CustomerHistoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the HistoryCommand
     * and returns a HistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomerHistoryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CustomerHistoryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomerHistoryCommand.MESSAGE_USAGE), pe);
        }
    }

}

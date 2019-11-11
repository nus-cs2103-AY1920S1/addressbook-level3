package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewCustomerWindowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCustomerWindowCommand object
 */
public class ViewCustomerWindowCommandParser implements Parser<ViewCustomerWindowCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCustomerWindowCommand
     * and returns an ViewCustomerWindowCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCustomerWindowCommand parse(String args) throws ParseException {
        try {
            int id = ParserUtil.parseId(args);
            return new ViewCustomerWindowCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCustomerWindowCommand.MESSAGE_USAGE), pe);
        }
    }
}

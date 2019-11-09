package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewCustomerTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCustomerTaskCommand object
 */
public class ViewCustomerTaskCommandParser implements Parser<ViewCustomerTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCustomerTaskCommand
     * and returns a ViewCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCustomerTaskCommand parse(String args) throws ParseException {

        try {
            int customerId = ParserUtil.parseId(args);
            return new ViewCustomerTaskCommand((customerId));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCustomerTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}

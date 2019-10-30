package seedu.address.logic.parser.employee;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.employee.FetchEmployeeCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new FetchEmployeeCommand object
 */
public class FetchEmployeeCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FetchEmployeeCommand
     * and returns a FetchEmployeeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FetchEmployeeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FetchEmployeeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FetchEmployeeCommand.MESSAGE_USAGE), pe);
        }
    }
}

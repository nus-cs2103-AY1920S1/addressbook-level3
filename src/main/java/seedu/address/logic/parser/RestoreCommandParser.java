package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RestoreCommand object
 */
public class RestoreCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a RestoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RestoreCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RestoreCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE), pe);
        }
    }
}

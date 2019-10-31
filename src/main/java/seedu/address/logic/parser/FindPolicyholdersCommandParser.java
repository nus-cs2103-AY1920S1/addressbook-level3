package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindPolicyholdersCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindPolicyholdersCommand object
 */
public class FindPolicyholdersCommandParser implements Parser<FindPolicyholdersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPolicyholdersCommand
     * and returns a FindPolicyholdersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPolicyholdersCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FindPolicyholdersCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPolicyholdersCommand.MESSAGE_USAGE), pe);
        }
    }

}

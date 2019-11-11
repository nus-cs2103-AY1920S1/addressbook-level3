package seedu.address.logic.finance.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.finance.commands.CloneCommand;
import seedu.address.logic.finance.commands.RepaidCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CloneCommand object
 */
public class CloneCommandParser implements Parser<CloneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CloneCommand
     * and returns a CloneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloneCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CloneCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RepaidCommand.MESSAGE_USAGE), pe);
        }
    }

}

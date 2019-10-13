package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.MemeDeleteCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MemeDeleteCommand object
 */
public class MemeDeleteCommandParser implements Parser<MemeDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemeDeleteCommand
     * and returns a MemeDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MemeDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}

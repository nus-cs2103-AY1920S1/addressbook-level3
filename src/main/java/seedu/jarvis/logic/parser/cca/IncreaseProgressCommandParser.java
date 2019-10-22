package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.cca.IncreaseProgressCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new IncreaseProgressCommand object
 */
public class IncreaseProgressCommandParser implements Parser<IncreaseProgressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the IncreaseProgressCommand
     * and returns a IncreaseProgressCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public IncreaseProgressCommand parse(String args) throws ParseException {
        try {
            Index index = CcaParserUtil.parseIndex(args);
            return new IncreaseProgressCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncreaseProgressCommand.MESSAGE_USAGE), pe);
        }
    }
}

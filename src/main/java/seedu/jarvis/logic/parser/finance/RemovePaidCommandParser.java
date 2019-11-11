package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.finance.RemovePaidCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new RemovePaidCommand object
 */
public class RemovePaidCommandParser implements Parser<RemovePaidCommand> {

    @Override
    public RemovePaidCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemovePaidCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePaidCommand.MESSAGE_USAGE, pe));
        }
    }
}

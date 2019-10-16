package seedu.address.logic.parser.visit;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.visit.BeginVisitCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BeginVisitCommand object
 */
public class BeginVisitCommandParser implements Parser<BeginVisitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BeginVisitCommand
     * and returns a BeginVisitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public BeginVisitCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new BeginVisitCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BeginVisitCommand.MESSAGE_USAGE), pe);
        }
    }
}

package seedu.weme.logic.parser.commandparser.exportcommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exportcommand.UnstageCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;

/**
 * Parses input arguments and creates a new UnstageCommand object.
 */
public class UnstageCommandParser implements Parser<UnstageCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnstageCommand
     * and returns a MemeUnstageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnstageCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnstageCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnstageCommand.MESSAGE_USAGE), pe);
        }
    }

}

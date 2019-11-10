package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ResetDisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new resetDisplayPictureCommand
 */
public class ResetDisplayPictureCommandParser implements Parser<ResetDisplayPictureCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ResetDisplayPictureCommand
     * and returns an ResetDisplayPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ResetDisplayPictureCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ResetDisplayPictureCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetDisplayPictureCommand.MESSAGE_USAGE), pe);
        }
    }
}

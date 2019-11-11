package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PreviewFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PreviewFileCommand object
 */
public class PreviewFileCommandParser implements FileCommandParser<PreviewFileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PreviewFileCommand
     * and returns a PreviewFileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PreviewFileCommand parse(String args, String password) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PreviewFileCommand(index, password);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreviewFileCommand.MESSAGE_USAGE), pe);
        }
    }

}

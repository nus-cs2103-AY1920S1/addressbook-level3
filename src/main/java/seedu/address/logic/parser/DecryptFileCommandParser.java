package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DecryptFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DecryptFileCommand object
 */
public class DecryptFileCommandParser implements FileCommandParser<DecryptFileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DecryptFileCommand parse(String args, String password) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DecryptFileCommand(index, password);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecryptFileCommand.MESSAGE_USAGE), pe);
        }
    }

}

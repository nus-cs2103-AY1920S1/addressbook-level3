package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReadPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReadPasswordCommand object
 */
public class ReadPasswordCommandParser implements Parser<ReadPasswordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReadPasswordCommand
     * and returns a ReadPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ReadPasswordCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new ReadPasswordCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadPasswordCommand.MESSAGE_USAGE));
        }
    }
}

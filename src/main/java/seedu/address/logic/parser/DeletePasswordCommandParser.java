package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePasswordCommand object
 */
public class DeletePasswordCommandParser implements Parser<DeletePasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePasswordCommand
     * and returns a DeletePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePasswordCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeletePasswordCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePasswordCommand.MESSAGE_USAGE));
        }
    }
}

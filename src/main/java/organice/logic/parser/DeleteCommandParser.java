package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import organice.logic.commands.DeleteCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Nric;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Nric nric = ParserUtil.parseNric(args);
            return new DeleteCommand(nric);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}

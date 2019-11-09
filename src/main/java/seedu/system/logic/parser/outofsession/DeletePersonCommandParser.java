package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.system.commons.core.index.Index;
import seedu.system.logic.commands.outofsession.DeletePersonCommand;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePersonCommand object.
 */
public class DeletePersonCommandParser implements Parser<DeletePersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePersonCommand
     * and returns a DeletePersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePersonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }

}

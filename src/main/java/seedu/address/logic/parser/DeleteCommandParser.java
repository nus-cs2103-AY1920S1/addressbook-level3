package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;

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
            if (args.trim().equals("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE));
            }
            IdentificationNumber idNum = ParserUtil.parseIdentificationNumber(args);
            return new DeleteCommand(idNum);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}

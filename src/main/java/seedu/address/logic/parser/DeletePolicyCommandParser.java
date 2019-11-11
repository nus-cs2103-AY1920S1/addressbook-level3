package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeletePolicyCommandParser implements Parser<DeletePolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePolicyCommand
     * and returns a DeletePolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePolicyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePolicyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE), pe);
        }
    }

}

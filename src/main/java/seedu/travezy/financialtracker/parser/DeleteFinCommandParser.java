package seedu.travezy.financialtracker.parser;

import seedu.travezy.commons.core.index.Index;
import seedu.travezy.financialtracker.commands.DeleteFinCommand;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.commons.core.Messages;

/**
 * Parses input arguments and creates a new DeleteFinCommand object
 */
public class DeleteFinCommandParser implements Parser<DeleteFinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFinCommand
     * and returns a DeleteFinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFinCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFinCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteFinCommand.MESSAGE_USAGE), pe);
        }
    }

}

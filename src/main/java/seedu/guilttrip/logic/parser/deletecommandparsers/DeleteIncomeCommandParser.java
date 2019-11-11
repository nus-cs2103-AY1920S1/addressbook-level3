package seedu.guilttrip.logic.parser.deletecommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.deletecommands.DeleteIncomeCommand;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteIncomeCommand object
 */
public class DeleteIncomeCommandParser implements Parser<DeleteIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIncomeCommand
     * and returns a DeleteIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteIncomeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteIncomeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteIncomeCommand.MESSAGE_USAGE), pe);
        }
    }

}

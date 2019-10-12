package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.DeletePlanCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeletePlanCommand object
 */
public class DeletePlanCommandParser implements Parser<DeletePlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePlanCommand
     * and returns a DeletePlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePlanCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePlanCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePlanCommand.MESSAGE_USAGE), pe);
        }
    }

}

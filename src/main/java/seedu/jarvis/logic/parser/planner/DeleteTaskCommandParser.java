package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.planner.DeleteTaskCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteTaskCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns a DeleteTaskCommand object for execution.
     * @param userInput the arguments to be
     * @return DeleteTaskCommand Object
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteTaskCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeleteTaskCommand(index);
        } catch (ParseException p) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE), p);
        }
    }
}

package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.planner.DoneTaskCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DoneTaskCommand object
 */
public class DoneTaskCommandParser implements Parser<DoneTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneTaskCommand
     * and returns a DoneTaskCommand object for execution
     * @param userInput the arguments to be parsed
     * @return DoneTaskCommand object
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public DoneTaskCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DoneTaskCommand(index);
        } catch (ParseException p) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneTaskCommand.MESSAGE_USAGE));
        }
    }
}

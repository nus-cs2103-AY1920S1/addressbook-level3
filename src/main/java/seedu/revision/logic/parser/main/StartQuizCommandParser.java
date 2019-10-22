package seedu.revision.logic.parser.main;

import seedu.revision.logic.commands.main.StartQuizCommand;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.exceptions.ParseException;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new StartQuizCommand object
 */
public class StartQuizCommandParser implements Parser<StartQuizCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StartQuizCommand
     * and returns a StartQuizCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StartQuizCommand parse(String args) throws ParseException {

        /*
        *TODO: Adapt from ListCommandParser.
         */
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartQuizCommand.MESSAGE_USAGE));
        }

        String questionKeywords = trimmedArgs;
//        return null;

        return new StartQuizCommand(questionKeywords);
    }
}

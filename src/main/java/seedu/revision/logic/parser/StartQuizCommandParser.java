package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StartQuizCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        return null;

//        return new StartQuizCommand(questionKeywords);
    }
}

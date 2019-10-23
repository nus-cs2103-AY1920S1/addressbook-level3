package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.quiz.commands.ViewAnswerCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;

/**
 *  Parses input arguments and creates a new ViewAnswerCommand object.
 */
public class ViewAnswerCommandParser implements Parser<ViewAnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAnswerCommand
     * and returns a ViewAnswerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewAnswerCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAnswerCommand.MESSAGE_USAGE));
        }

        return new ViewAnswerCommand(args);
    }
}

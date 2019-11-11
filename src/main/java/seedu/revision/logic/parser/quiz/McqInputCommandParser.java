package seedu.revision.logic.parser.quiz;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.ui.bar.Timer.TIMER_UP_SKIP_QUESTION;

import seedu.revision.logic.commands.quiz.McqInputCommand;
import seedu.revision.logic.parser.QuizParser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class McqInputCommandParser implements QuizParser<McqInputCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public McqInputCommand parse(String args, Answerable currentAnswerable) throws ParseException {
        if (args.matches("\\b(?i)[a-d]\\b") || args.equals(TIMER_UP_SKIP_QUESTION)) {
            return new McqInputCommand(args, currentAnswerable);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, McqInputCommand.MESSAGE_USAGE));
        }
    }
}

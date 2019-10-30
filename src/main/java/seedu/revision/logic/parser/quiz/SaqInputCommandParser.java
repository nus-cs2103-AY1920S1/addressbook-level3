package seedu.revision.logic.parser.quiz;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.revision.logic.commands.quiz.SaqInputCommand;
import seedu.revision.logic.parser.QuizParser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SaqInputCommandParser implements QuizParser<SaqInputCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input is blank
     */
    public SaqInputCommand parse(String args, Answerable currentAnswerable) throws ParseException {

        if (Character.isLetter(args.charAt(0)) || Character.isDigit(args.charAt(0))) {
            return new SaqInputCommand(args, currentAnswerable);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaqInputCommand.MESSAGE_USAGE));
        }
    }
}

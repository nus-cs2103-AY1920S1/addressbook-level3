package seedu.address.logic.parser.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.quiz.QuizShowAnswerCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new QuizShowAnswerCommand object.
 */
public class QuizShowAnswerParser implements Parser<QuizShowAnswerCommand> {
    public static final String INVALID_INDEX = "The index input should be a positive integer!";

    private int index;

    public QuizShowAnswerCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            index = Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new ParseException(INVALID_INDEX);
        }
        return new QuizShowAnswerCommand(index);
    }
}

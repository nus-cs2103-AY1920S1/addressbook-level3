package seedu.address.logic.parser.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.quiz.QuizShowAnswerCommand;
import seedu.address.logic.parser.Parser;

/**
 * Parses input arguments and creates a new QuizShowAnswerCommand object.
 */
public class QuizShowAnswerParser implements Parser<QuizShowAnswerCommand> {
    /**
     * Parses the given {@code String} of user input in the context of showing answer
     * and returns an QuizShowAnswerCommand object for execution.
     */
    public QuizShowAnswerCommand parse(String args) {
        requireNonNull(args);

        return new QuizShowAnswerCommand();
    }
}

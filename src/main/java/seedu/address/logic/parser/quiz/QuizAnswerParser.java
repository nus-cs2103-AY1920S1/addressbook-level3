package seedu.address.logic.parser.quiz;

import seedu.address.logic.commands.quiz.QuizCheckAnswer;
import seedu.address.logic.parser.Parser;
import seedu.address.model.question.Answer;

/**
 * Parses user input for quiz.
 */
public class QuizAnswerParser implements Parser<QuizCheckAnswer> {
    /**
     * Parses the given {@code String} of user input in the context of the quiz answer
     * and returns an QuizCheckAnswer object for execution.
     */
    public QuizCheckAnswer parse(String userInput) {
        final Answer answer = new Answer(userInput.trim());

        return new QuizCheckAnswer(answer);
    }
}

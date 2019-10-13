package seedu.address.logic.parser.quiz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.quiz.QuizCheckAnswer;
import seedu.address.logic.parser.Parser;
import seedu.address.model.question.Answer;

/**
 * Parses user input for quiz.
 */
public class QuizAnswerParser implements Parser<QuizCheckAnswer> {
    /**
     * Used for initial separation of question index and answer.
     */
    private static final Pattern BASIC_INPUT_FORMAT = Pattern.compile("(?<index>\\s+)(?<answer>)");

    public QuizCheckAnswer parse(String userInput) {
        final Matcher matcher = BASIC_INPUT_FORMAT.matcher(userInput.trim());

        final String index = matcher.group("index");
        final String stringAnswer = matcher.group("answer");
        final int intIndex = Integer.parseInt(index);
        final Answer answer = new Answer(stringAnswer);

        return new QuizCheckAnswer(intIndex, answer);
    }
}

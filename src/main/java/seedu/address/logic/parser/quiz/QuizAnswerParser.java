package seedu.address.logic.parser.quiz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.quiz.QuizCheckAnswer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.Answer;

/**
 * Parses user input for quiz.
 */
public class QuizAnswerParser implements Parser<QuizCheckAnswer> {
    private static final String ANSWER_INVALID_FORMAT = "Invalid answer format! \n%1$s";
    private static final String SHOW_ANSWER_FORMAT = "Format: INDEX:ANSWER\n" + "Example: 1:abstraction";

    /**
     * Used for initial separation of question index and answer.
     */
    private static final Pattern BASIC_INPUT_FORMAT = Pattern.compile("(?<index>\\d+):(?<answer>.+)");

    /**
     * Parses the given {@code String} of user input in the context of the quiz answer
     * and returns an QuizCheckAnswer object for execution.
     */
    public QuizCheckAnswer parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_INPUT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(ANSWER_INVALID_FORMAT, SHOW_ANSWER_FORMAT));
        }

        final String index = matcher.group("index");
        final String stringAnswer = matcher.group("answer");
        final int intIndex = Integer.parseInt(index) - 1;
        final Answer answer = new Answer(stringAnswer);

        return new QuizCheckAnswer(intIndex, answer);
    }
}

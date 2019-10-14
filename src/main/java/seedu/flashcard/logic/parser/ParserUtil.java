package seedu.flashcard.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.McqQuestion;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerQuestion;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {


    /**
     * Parses a {@code String question} into a {@code ShortAnswerQuestion}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Question parseShortAnswerQuestion(String question)  {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        return new ShortAnswerQuestion(trimmedQuestion);
    }

    /**
     * Parses a {@code String question} into a {@code ShortAnswerQuestion}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Answer parseAnswer(String answer) {
        requireNonNull(answer);
        String trimmedQuestion = answer.trim();
        return new Answer(trimmedQuestion);
    }


}

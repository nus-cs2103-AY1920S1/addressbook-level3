package seedu.revision.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.revision.commons.core.index.Index;
import seedu.revision.commons.util.StringUtil;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.QuestionType;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;
import seedu.revision.model.quiz.ArcadeMode;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalMode;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";


    /**
     * Parses a {@code String answer} into a {@code Answer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code answer} is invalid.
     */
    public static Answer parseAnswer(String answer) throws ParseException {
        requireNonNull(answer);
        String trimmedAnswer = answer.trim();
        if (!Answer.isValidAnswer(trimmedAnswer)) {
            throw new ParseException(Answer.MESSAGE_CONSTRAINTS);
        }
        return new Answer(trimmedAnswer);
    }

    /**
     * Parses {@code Collection<String> answers} into a {@code Set<Answer>}.
     */
    public static ArrayList<Answer> parseAnswers(Collection<String> answers) throws ParseException {
        requireNonNull(answers);
        final ArrayList<Answer> answerList = new ArrayList<>();
        for (String answer : answers) {
            Answer newAnswer = parseAnswer(answer);
            if (answerList.contains(newAnswer)) {
                throw new ParseException(Answer.MESSAGE_CONSTRAINTS);
            }
            answerList.add(newAnswer);
        }
        return answerList;
    }

    /**
     * Parses {@code questionType} into an {@code QuestionType} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the given {@code questionType} is invalid.
     */
    public static QuestionType parseType(String questionType) throws ParseException {
        String trimmedType = questionType.trim();
        if (!QuestionType.isValidQuestionType(trimmedType)) {
            throw new ParseException(QuestionType.MESSAGE_CONSTRAINTS);
        }
        return new QuestionType(trimmedType);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String question} into a {@code Question}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code question} is invalid.
     */
    public static Question parseQuestion(String question) throws ParseException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new ParseException(Question.MESSAGE_CONSTRAINTS);
        }
        return new Question(trimmedQuestion);
    }

    /**
     * Parses a {@code String difficulty} into a {@code Difficulty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code difficulty} is invalid.
     */
    public static Difficulty parseDifficulty(String difficulty) throws ParseException {
        requireNonNull(difficulty);
        String trimmedDifficulty = difficulty.trim();
        if (!Difficulty.isValidDifficulty(trimmedDifficulty)) {
            throw new ParseException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        return new Difficulty(trimmedDifficulty);
    }


    /**
     * Parses a {@code String Category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategoryName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code Collection<String> Categorys} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories(Collection<String> categories) throws ParseException {
        requireNonNull(categories);
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(parseCategory(categoryName));
        }
        return categorySet;
    }

    /**
     * Parses a {@code String mode} into a {@code Mode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code mode} is invalid.
     */
    public static Mode parseMode(String mode) throws ParseException {
        requireNonNull(mode);
        String trimmedMode = mode.trim();
        if (!Mode.isValidMode(trimmedMode)) {
            throw new ParseException(Mode.MESSAGE_CONSTRAINTS);
        }
        switch (trimmedMode) {
        case "normal":
            return new NormalMode();
        case "arcade":
            return new ArcadeMode();
        //case "custom":
        default:
            throw new ParseException("Invalid mode found at ParserUtil");
        }
    }

}

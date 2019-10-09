package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.util.FilePath;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String rating} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rating} is invalid.
     */
    public static Rating parseRating (String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
        }
        return new Rating(trimmedRating);
    }

    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory (String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategoryName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories (Collection<String> categories) throws ParseException {
        requireNonNull(categories);
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(parseCategory(categoryName));
        }
        return categorySet;
    }

    /**
     * Parses a {@code String filePath} into a {@code FilePath}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filePath} is invalid.
     */
    public static FilePath parseFilePath (String filePath) throws ParseException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        if (!FilePath.isValidFilePath((trimmedFilePath))) {
            throw new ParseException(FilePath.MESSAGE_CONSTRAINTS);
        }
        return new FilePath(trimmedFilePath);
    }

}

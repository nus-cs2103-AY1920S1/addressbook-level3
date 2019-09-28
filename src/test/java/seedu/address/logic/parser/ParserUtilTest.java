package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;

public class ParserUtilTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_ANSWER = " ";
    private static final String INVALID_RATING = " ";
    private static final String INVALID_CATEGORY = "#friend";

    private static final String VALID_QUESTION = "2 + 2";
    private static final String VALID_ANSWER = "4";
    private static final String VALID_RATING = "good";
    private static final String VALID_CATEGORY_1 = "math";
    private static final String VALID_CATEGORY_2 = "science";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("-10"));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_FLASHCARD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_FLASHCARD, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseQuestion_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseQuestion((String) null));
    }

    @Test
    public void parseQuestion_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuestion(INVALID_QUESTION));
    }

    @Test
    public void parseQuestion_validValueWithoutWhitespace_returnsQuestion() throws Exception {
        Question expectedQuestion = new Question(VALID_QUESTION);
        assertEquals(expectedQuestion, ParserUtil.parseQuestion(VALID_QUESTION));
    }

    @Test
    public void parseQuestion_validValueWithWhitespace_returnsTrimmedQuestion() throws Exception {
        String questionWithWhitespace = WHITESPACE + VALID_QUESTION + WHITESPACE;
        Question expectedQuestion = new Question(VALID_QUESTION);
        assertEquals(expectedQuestion, ParserUtil.parseQuestion(questionWithWhitespace));
        questionWithWhitespace = WHITESPACE + WHITESPACE + VALID_QUESTION + WHITESPACE + WHITESPACE;
        assertEquals(expectedQuestion, ParserUtil.parseQuestion(questionWithWhitespace));
        questionWithWhitespace = WHITESPACE + VALID_QUESTION;
        assertEquals(expectedQuestion, ParserUtil.parseQuestion(questionWithWhitespace));
        questionWithWhitespace = VALID_QUESTION + WHITESPACE;
        assertEquals(expectedQuestion, ParserUtil.parseQuestion(questionWithWhitespace));
    }

    @Test
    public void parseAnswer_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAnswer((String) null));
    }

    @Test
    public void parseAnswer_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAnswer(INVALID_ANSWER));
    }

    @Test
    public void parseAnswer_validValueWithoutWhitespace_returnsAnswer() throws Exception {
        Answer expectedAnswer = new Answer(VALID_ANSWER);
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(VALID_ANSWER));
    }

    @Test
    public void parseAnswer_validValueWithWhitespace_returnsTrimmedAnswer() throws Exception {
        String answerWithWhitespace = WHITESPACE + VALID_ANSWER + WHITESPACE;
        Answer expectedAnswer = new Answer(VALID_ANSWER);
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(answerWithWhitespace));
        answerWithWhitespace = WHITESPACE + WHITESPACE + VALID_ANSWER + WHITESPACE + WHITESPACE;
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(answerWithWhitespace));
        answerWithWhitespace = WHITESPACE + VALID_ANSWER;
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(answerWithWhitespace));
        answerWithWhitespace = VALID_ANSWER + WHITESPACE;
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(answerWithWhitespace));
    }

    @Test
    public void parseRating_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRating((String) null));
    }

    @Test
    public void parseRating_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRating(INVALID_RATING));
    }

    @Test
    public void parseRating_validValueWithoutWhitespace_returnsRating() throws Exception {
        Rating expectedRating = new Rating(VALID_RATING);
        assertEquals(expectedRating, ParserUtil.parseRating(VALID_RATING));
    }

    @Test
    public void parseRating_validValueWithWhitespace_returnsTrimmedRating() throws Exception {
        String ratingWithWhitespace = WHITESPACE + VALID_RATING + WHITESPACE;
        Rating expectedRating = new Rating(VALID_RATING);
        assertEquals(expectedRating, ParserUtil.parseRating(ratingWithWhitespace));
        ratingWithWhitespace = WHITESPACE + WHITESPACE + VALID_RATING + WHITESPACE + WHITESPACE;
        assertEquals(expectedRating, ParserUtil.parseRating(ratingWithWhitespace));
        ratingWithWhitespace = WHITESPACE + VALID_RATING;
        assertEquals(expectedRating, ParserUtil.parseRating(ratingWithWhitespace));
        ratingWithWhitespace = VALID_RATING + WHITESPACE;
        assertEquals(expectedRating, ParserUtil.parseRating(ratingWithWhitespace));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory(null));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_CATEGORY_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_CATEGORY_1));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY_1 + WHITESPACE;
        Category expectedCategory = new Category(VALID_CATEGORY_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
        categoryWithWhitespace = WHITESPACE + WHITESPACE + VALID_CATEGORY_1 + WHITESPACE + WHITESPACE;
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
        categoryWithWhitespace = WHITESPACE + VALID_CATEGORY_1;
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
        categoryWithWhitespace = VALID_CATEGORY_1 + WHITESPACE;
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
    }

    @Test
    public void parseCategories_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategories(null));
    }

    @Test
    public void parseCategories_collectionWithInvalidCategories_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseCategories(Arrays.asList(VALID_CATEGORY_1, INVALID_CATEGORY)));
    }

    @Test
    public void parseCategories_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseCategories(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseCategories_collectionWithValidCategories_returnsCategorySet() throws Exception {
        Set<Category> actualCategorySet =
                ParserUtil.parseCategories(Arrays.asList(VALID_CATEGORY_1, VALID_CATEGORY_2));
        Set<Category> expectedCategorySet =
                new HashSet<Category>(
                        Arrays.asList(new Category(VALID_CATEGORY_1), new Category(VALID_CATEGORY_2)));

        assertEquals(expectedCategorySet, actualCategorySet);
    }
}

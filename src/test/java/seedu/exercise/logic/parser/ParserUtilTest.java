package seedu.exercise.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.exercise.Calories;
import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Name;
import seedu.exercise.model.exercise.Quantity;
import seedu.exercise.model.tag.Muscle;

public class ParserUtilTest {
    private static final String INVALID_NAME = "D@nce";
    private static final String INVALID_DATE = "33/20/2019";
    private static final String INVALID_QUANTITY = " ";
    private static final String INVALID_CALORIES = "33a";
    private static final String INVALID_MUSCLE = "32friend";

    private static final String VALID_NAME = "Dance";
    private static final String VALID_DATE = "26/09/2019";
    private static final String VALID_QUANTITY = "3";
    private static final String VALID_CALORIES = "321";
    private static final String VALID_MUSCLE_1 = "Legs";
    private static final String VALID_MUSCLE_2 = "Arms";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_EXERCISE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_EXERCISE, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseCalories_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCalories((String) null));
    }

    @Test
    public void parseCalories_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCalories(INVALID_CALORIES));
    }

    @Test
    public void parseCalories_validValueWithoutWhitespace_returnsCalories() throws Exception {
        Calories expectedCalories = new Calories(VALID_CALORIES);
        assertEquals(expectedCalories, ParserUtil.parseCalories(VALID_CALORIES));
    }

    @Test
    public void parseCalories_validValueWithWhitespace_returnsCaloriesPhone() throws Exception {
        String caloriesWithWhitespace = WHITESPACE + VALID_CALORIES + WHITESPACE;
        Calories expectedCalories = new Calories(VALID_CALORIES);
        assertEquals(expectedCalories, ParserUtil.parseCalories(caloriesWithWhitespace));
    }

    @Test
    public void parseQuantity_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseQuantity((String) null));
    }

    @Test
    public void parseQuantity_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity(INVALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithoutWhitespace_returnsQuantity() throws Exception {
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(VALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithWhitespace_returnsTrimmedQuantity() throws Exception {
        String quantityWithWhitespace = WHITESPACE + VALID_QUANTITY + WHITESPACE;
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(quantityWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseMuscle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMuscle(null));
    }

    @Test
    public void parseMuscle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMuscle(INVALID_MUSCLE));
    }

    @Test
    public void parseMuscle_validValueWithoutWhitespace_returnsMuscle() throws Exception {
        Muscle expectedMuscle = new Muscle(VALID_MUSCLE_1);
        assertEquals(expectedMuscle, ParserUtil.parseMuscle(VALID_MUSCLE_1));
    }

    @Test
    public void parseMuscle_validValueWithWhitespace_returnsTrimmedMuscle() throws Exception {
        String muscleWithWhitespace = WHITESPACE + VALID_MUSCLE_1 + WHITESPACE;
        Muscle expectedMuscle = new Muscle(VALID_MUSCLE_1);
        assertEquals(expectedMuscle, ParserUtil.parseMuscle(muscleWithWhitespace));
    }

    @Test
    public void parseMuscles_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMuscles(null));
    }

    @Test
    public void parseMuscles_collectionWithInvalidMuscles_throwsParseException() {
        assertThrows(ParseException.class, () ->
            ParserUtil.parseMuscles(Arrays.asList(VALID_MUSCLE_1, INVALID_MUSCLE)));
    }

    @Test
    public void parseMuscles_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseMuscles(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseMuscles_collectionWithValidMuscles_returnsMuscleSet() throws Exception {
        Set<Muscle> actualMuscleSet = ParserUtil.parseMuscles(Arrays.asList(VALID_MUSCLE_1, VALID_MUSCLE_2));
        Set<Muscle> expectedMuscleSet =
            new HashSet<Muscle>(Arrays.asList(new Muscle(VALID_MUSCLE_1), new Muscle(VALID_MUSCLE_2)));

        assertEquals(expectedMuscleSet, actualMuscleSet);
    }
}

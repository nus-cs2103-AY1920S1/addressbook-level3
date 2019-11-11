package seedu.exercise.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.commons.core.index.Index.MESSAGE_CONSTRAINTS;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.logic.parser.predicate.ExerciseCustomPropertyPredicate;
import seedu.exercise.logic.parser.predicate.ExerciseMusclePredicate;
import seedu.exercise.logic.parser.predicate.ExercisePredicate;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.property.custom.ParameterType;

public class ParserUtilTest {
    private static final String INVALID_NAME = "D@nce";
    private static final String INVALID_DATE = "33/20/2019";
    private static final String INVALID_QUANTITY = "123a";
    private static final String INVALID_CALORIES = "33a";
    private static final String INVALID_UNIT = " ";
    private static final String INVALID_MUSCLE = "32friend";
    private static final String INVALID_END_DATE = "25/06/2019";
    private static final String INVALID_CATEGORY = "rgime";
    private static final String INVALID_CHART = "llchart";
    private static final String INVALID_STATISTIC_CATEGORY = "clories";

    private static final String VALID_NAME = "Dance";
    private static final String VALID_DATE = "26/09/2019";
    private static final String VALID_QUANTITY = "3";
    private static final String VALID_CALORIES = "321";
    private static final String VALID_UNIT = "km";
    private static final String VALID_MUSCLE_1 = "Legs";
    private static final String VALID_MUSCLE_2 = "Arms";
    private static final String VALID_CUSTOM_PROPERTY_PREFIX_NAME = "r";
    private static final String VALID_CUSTOM_PROPERTY_FULL_NAME = "Rating";
    private static final String VALID_CUSTOM_PROPERTY_VALUE = "1";
    private static final String VALID_END_DATE = "27/09/2019";
    private static final String VALID_CATEGORY = "regime";
    private static final String VALID_CHART = "linechart";
    private static final String VALID_STATISTIC_CATEGORY = "calories";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_CONSTRAINTS, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_ONE_BASED_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_ONE_BASED_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseIndexes_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIndexes(null));
    }

    @Test
    public void parseIndexes_collectionWithInvalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () ->
            ParserUtil.parseIndexes(Arrays.asList("-1", "0")));
    }

    @Test
    public void parseIndexes_emptyCollection_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseIndexes(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseIndexes_collectionWithValidMuscles_returnsIndexList() throws Exception {
        ArrayList<Index> actualIndexSet = ParserUtil.parseIndexes(Arrays.asList("1", "2"));
        ArrayList<Index> expectedIndexSet = new ArrayList<>(Arrays.asList(INDEX_ONE_BASED_FIRST,
            INDEX_ONE_BASED_SECOND));

        assertEquals(expectedIndexSet, actualIndexSet);
    }

    @Test
    public void parseName_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
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
    public void parseCalories_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCalories(null));
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
    public void parseQuantity_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseQuantity(null));
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
    public void parseDate_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
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
    public void parseUnit_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUnit(null));
    }

    @Test
    public void parseUnit_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseUnit(INVALID_UNIT));
    }

    @Test
    public void parseUnit_validValueWithoutWhitespace_returnsUnit() throws Exception {
        Unit expectedUnit = new Unit(VALID_UNIT);
        assertEquals(expectedUnit, ParserUtil.parseUnit(VALID_UNIT));
    }

    @Test
    public void parseUnit_validValueWithWhitespace_returnsTrimmedUnit() throws Exception {
        String unitWithWhitespace = WHITESPACE + VALID_UNIT + WHITESPACE;
        Unit expectedUnit = new Unit(VALID_UNIT);
        assertEquals(expectedUnit, ParserUtil.parseUnit(unitWithWhitespace));
    }

    @Test
    public void parseMuscle_withNull_throwsNullPointerException() {
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
    public void parseMuscles_withNull_throwsNullPointerException() {
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

    @Test
    public void parseFullName_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFullName(null));
    }

    @Test
    public void parseFullName_invalidFullName_throwsParseException() {
        // Contain punctuation -> invalid
        assertThrows(ParseException.class, () -> ParserUtil.parseFullName("Inv@lid"));

        // Contain number -> invalid
        assertThrows(ParseException.class, () -> ParserUtil.parseFullName("1nval1d"));
    }

    @Test
    public void parseFullName_validFullName_returnsFormattedFullName() throws Exception {
        // Single letter -> valid
        String singleLetter = "g";
        assertEquals("G", ParserUtil.parseFullName(singleLetter));

        // Randomly stylised string with whitespaces -> valid
        String longName = "ThIs iS a     VaLid    NaME     with lOTs of SPACEs";
        assertEquals("This Is A Valid Name With Lots Of Spaces", ParserUtil.parseFullName(longName));

        // Correctly stylised string with whitespaces in front -> valid
        String shortName = "   Short";
        assertEquals("Short", ParserUtil.parseFullName(shortName));
    }

    @Test
    public void parsePrefixName_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrefixName(null));
    }

    @Test
    public void parsePrefixName_invalidPrefixName_throwsParseException() {
        // EP: Contains number
        assertThrows(ParseException.class, () -> ParserUtil.parsePrefixName("33"));

        // EP: Contains punctuation
        assertThrows(ParseException.class, () -> ParserUtil.parsePrefixName("?!"));

        // EP: Contains whitespace
        assertThrows(ParseException.class, () -> ParserUtil.parsePrefixName("h i"));

        // EP: Mix of punctuation, numbers and alphabets
        assertThrows(ParseException.class, () -> ParserUtil.parsePrefixName("invalid1234?!"));
    }

    @Test
    public void parsePrefixName_validPrefixName_returnsPrefix() throws Exception {
        // Single letter -> valid
        String singleLetter = "r";
        Prefix expectedPrefix1 = new Prefix("r/");
        assertEquals(expectedPrefix1, ParserUtil.parsePrefixName(singleLetter));

        // Multiple letters with no space -> valid
        String multipleLetters = "rrrmd";
        Prefix expectedPrefix2 = new Prefix("rrrmd/");
        assertEquals(expectedPrefix2, ParserUtil.parsePrefixName(multipleLetters));

        // Randomly stylised letters with no space -> valid
        String randomlyStylised = "RmRDRd";
        Prefix expectedPrefix3 = new Prefix("RmRDRd/");
        assertEquals(expectedPrefix3, ParserUtil.parsePrefixName(randomlyStylised));
    }

    @Test
    public void parseParameterType_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseParameterType(null));
    }

    @Test
    public void parseParameterType_invalidParameterType_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseParameterType(" "));
        assertThrows(ParseException.class, () -> ParserUtil.parseParameterType("gG"));
        assertThrows(ParseException.class, () -> ParserUtil.parseParameterType("123"));
        assertThrows(ParseException.class, () -> ParserUtil.parseParameterType("?!"));
    }

    @Test
    public void parseParameterType_validParameterType_returnsParameterType() throws Exception {
        // String "Number" -> valid
        assertEquals(ParameterType.NUMBER, ParserUtil.parseParameterType("Number"));

        // String "Date" -> valid
        assertEquals(ParameterType.DATE, ParserUtil.parseParameterType("Date"));

        // String "Text" -> valid
        assertEquals(ParameterType.TEXT, ParserUtil.parseParameterType("Text"));
    }

    @Test
    public void parseCustomProperties_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCustomProperties(null));
    }

    @Test
    public void parseSuggestType_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSuggestType(null));
    }

    @Test
    public void parseOperationType_withOperationType_returnsIsStrict() throws Exception {
        //String "and" -> true
        assertEquals(true, ParserUtil.parseOperationType("and"));

        //String "or" -> false
        assertEquals(false, ParserUtil.parseOperationType("or"));
    }

    @Test
    public void parseOperationType_withInvalidOperationType_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOperationType("basicallyimpossible"));
    }

    @Test
    public void parsePredicate_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            ParserUtil.parsePredicate(null, new TreeMap<>(), true));
        assertThrows(NullPointerException.class, () ->
            ParserUtil.parsePredicate(new HashSet<>(), null, true));
    }

    @Test
    public void parsePredicate_atLeastOnePredicate_returnExercisePredicate() throws Exception {
        //only muscles, empty custom properties
        Set<Muscle> muscles = new HashSet<>();
        muscles.add(new Muscle(VALID_MUSCLE_1));
        assertEquals(new ExercisePredicate(false, new ExerciseMusclePredicate(muscles, false)),
            ParserUtil.parsePredicate(muscles, new TreeMap<>(), false));

        //only custom properties, empty muscles
        Map<String, String> customProperties = new TreeMap<>();
        customProperties.put(VALID_CUSTOM_PROPERTY_FULL_NAME, VALID_CUSTOM_PROPERTY_VALUE);
        assertEquals(new ExercisePredicate(true, new ExerciseCustomPropertyPredicate(customProperties, true)),
            ParserUtil.parsePredicate(new HashSet<>(), customProperties, true));

        //both muscles, and custom properties
        assertEquals(new ExercisePredicate(true,
                new ExerciseMusclePredicate(muscles, true),
                new ExerciseCustomPropertyPredicate(customProperties, true)),
            ParserUtil.parsePredicate(muscles, customProperties, true));
    }

    @Test
    public void parsePredicate_twoEmptyPredicates_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePredicate(new HashSet<>(), new TreeMap<>(), true));
    }

    @Test
    public void parseEndDate_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEndDate(null, null));
    }

    @Test
    public void parseEndDate_invalidValue_throwsParseException() {
        Date validDate = new Date(VALID_DATE);
        assertThrows(ParseException.class, () -> ParserUtil.parseEndDate(validDate, INVALID_END_DATE));
    }

    @Test
    public void parseEndDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date validDate = new Date(VALID_DATE);
        Date expectedDate = new Date(VALID_END_DATE);
        assertEquals(expectedDate, ParserUtil.parseEndDate(validDate, VALID_END_DATE));
    }

    @Test
    public void parseEndDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_END_DATE + WHITESPACE;
        Date validDate = new Date(VALID_DATE);
        Date expectedDate = new Date(VALID_END_DATE);
        assertEquals(expectedDate, ParserUtil.parseEndDate(validDate, dateWithWhitespace));
    }

    @Test
    public void parseCategory_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory(null));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace() throws Exception {
        assertEquals(VALID_CATEGORY, ParserUtil.parseCategory(VALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithWhitespace() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY + WHITESPACE;
        assertEquals(VALID_CATEGORY, ParserUtil.parseCategory(categoryWithWhitespace));
    }

    @Test
    public void parseChart_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseChart(null));
    }

    @Test
    public void parseChart_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseChart(INVALID_CHART));
    }

    @Test
    public void parseChart_validValueWithoutWhitespace() throws Exception {
        assertEquals(VALID_CHART, ParserUtil.parseChart(VALID_CHART));
    }

    @Test
    public void parseChart_validValueWithWhitespace() throws Exception {
        String chartWithWhitespace = WHITESPACE + VALID_CHART + WHITESPACE;
        assertEquals(VALID_CHART, ParserUtil.parseChart(chartWithWhitespace));
    }

    @Test
    public void parseStatisticCategory_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatisticCategory(null));
    }

    @Test
    public void parseStatisticCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatisticCategory(INVALID_STATISTIC_CATEGORY));
    }

    @Test
    public void parseStatisticCategory_validValueWithoutWhitespace() throws Exception {
        assertEquals(VALID_STATISTIC_CATEGORY, ParserUtil.parseStatisticCategory(VALID_STATISTIC_CATEGORY));
    }

    @Test
    public void parseStatisticCategory_validValueWithWhitespace() throws Exception {
        String statisticCategoryWithWhitespace = WHITESPACE + VALID_STATISTIC_CATEGORY + WHITESPACE;
        assertEquals(VALID_STATISTIC_CATEGORY, ParserUtil.parseStatisticCategory(statisticCategoryWithWhitespace));
    }
}

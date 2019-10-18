package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Name;
import seedu.address.model.diary.components.DiaryName;
import seedu.address.model.profile.medical.MedicalHistory;
import seedu.address.model.recipe.components.Ingredient;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String INVALID_NAME_2 = "T@mago Maki";
    private static final String INVALID_INGREDIENT = "Nor!";

    private static final String INVALID_DIARY_NAME = "R@ndom name";
    private static final String VALID_DIARY_NAME = "Test Diary";

    private static final String VALID_NAME_2 = "Tamago Maki";
    private static final String VALID_INGREDIENT_1 = "Eggs";
    private static final String VALID_INGREDIENT_2 = "Nori";

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
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));

        // No whitespaces
        assertEquals(INDEX_FIRST_RECIPE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_RECIPE, ParserUtil.parseIndex("  1  "));

        // No whitespaces
        assertEquals(INDEX_FIRST_DIARY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_DIARY, ParserUtil.parseIndex("  1  "));
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
    public void parseDiaryName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDiaryName((String) null));
    }

    @Test
    public void parseDiaryName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDiaryName(INVALID_DIARY_NAME));
    }

    @Test
    public void parseDiaryName_validValueWithoutWhitespace_returnsName() throws Exception {
        DiaryName expectedName = new DiaryName(VALID_DIARY_NAME);
        assertEquals(expectedName, ParserUtil.parseDiaryName(VALID_DIARY_NAME));
    }

    @Test
    public void parseDiaryName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_DIARY_NAME + WHITESPACE;
        DiaryName expectedName = new DiaryName(VALID_DIARY_NAME);
        assertEquals(expectedName, ParserUtil.parseDiaryName(nameWithWhitespace));
    }

    @Test
    public void parseMedicalHistory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedicalHistory(null));
    }

    @Test
    public void parseMedicalHistory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicalHistory(INVALID_TAG));
    }

    @Test
    public void parseMedicalHistory_validValueWithoutWhitespace_returnsMedicalHistory() throws Exception {
        MedicalHistory expectedMedicalHistory = new MedicalHistory(VALID_TAG_1);
        assertEquals(expectedMedicalHistory, ParserUtil.parseMedicalHistory(VALID_TAG_1));
    }

    @Test
    public void parseMedicalHistory_validValueWithWhitespace_returnsTrimmedMedicalHistory() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        MedicalHistory expectedMedicalHistory = new MedicalHistory(VALID_TAG_1);
        assertEquals(expectedMedicalHistory, ParserUtil.parseMedicalHistory(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedicalHistories(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
                .parseMedicalHistories(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseMedicalHistories(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<MedicalHistory> actualMedicalHistorySet =
                ParserUtil.parseMedicalHistories(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<MedicalHistory> expectedMedicalHistorySet = new HashSet<MedicalHistory>(
                Arrays.asList(new MedicalHistory(VALID_TAG_1), new MedicalHistory(VALID_TAG_2)));

        assertEquals(expectedMedicalHistorySet, actualMedicalHistorySet);
    }

    @Test
    public void parseIngredient_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredient(null));
    }

    @Test
    public void parseIngredient_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIngredient(INVALID_INGREDIENT));
    }

    @Test
    public void parseIngredient_validValueWithoutWhitespace_returnsIngredient() throws Exception {
        Ingredient expectedIngredient = new Ingredient(VALID_INGREDIENT_1);
        assertEquals(expectedIngredient, ParserUtil.parseIngredient(VALID_INGREDIENT_1));
    }

    @Test
    public void parseIngredient_validValueWithWhitespace_returnsTrimmedIngredient() throws Exception {
        String ingredientWithWhitespace = WHITESPACE + VALID_INGREDIENT_1 + WHITESPACE;
        Ingredient expectedIngredient = new Ingredient(VALID_INGREDIENT_1);
        assertEquals(expectedIngredient, ParserUtil.parseIngredient(ingredientWithWhitespace));
    }

    @Test
    public void parseIngredients_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredients(null));
    }

    @Test
    public void parseIngredients_collectionWithInvalidIngredients_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIngredients(Arrays.asList(VALID_INGREDIENT_1,
                INVALID_INGREDIENT)));
    }

    @Test
    public void parseIngredients_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseIngredients(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseIngredients_collectionWithValidIngredients_returnsIngredientSet() throws Exception {
        Set<Ingredient> actualIngredientSet = ParserUtil.parseIngredients(Arrays.asList(VALID_INGREDIENT_1,
                VALID_INGREDIENT_2));
        Set<Ingredient> expectedIngredientSet = new HashSet<Ingredient>(Arrays.asList(
                new Ingredient(VALID_INGREDIENT_1), new Ingredient(VALID_INGREDIENT_2)));

        assertEquals(expectedIngredientSet, actualIngredientSet);
    }
}

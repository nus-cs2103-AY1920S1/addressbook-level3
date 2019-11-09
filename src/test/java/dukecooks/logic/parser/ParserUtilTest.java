package dukecooks.logic.parser;

import static dukecooks.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static dukecooks.testutil.Assert.assertThrows;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_DASHBOARD;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.Image;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.model.dashboard.components.TaskDate;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.Name;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.RecipeName;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_TASKNAME = "Project Meeting";
    private static final String INVALID_TASKNAME = "Project Meeting!";

    private static final String VALID_TASKDATE = "12/12/2019";
    private static final String INVALID_TASKDATE = "12.12.2019";

    private static final String INVALID_NAME_2 = "T@mago Maki";
    private static final String INVALID_INGREDIENT = "Nor!";

    private static final String INVALID_DIARY_NAME = "R@ndom name";
    private static final String VALID_DIARY_NAME = "Test Diary";

    private static final String INVALID_PAGE_TITLE = "Hello@@";
    private static final String VALID_PAGE_TITLE = "Hello";

    private static final String INVALID_PAGE_TYPE = "whatever";
    private static final String VALID_PAGE_TYPE = "health";

    private static final String INVALID_PAGE_IMAGE = "asdadada.txt";
    private static final String VALID_PAGE_IMAGE = "/images/pho.jpg";

    private static final String INVALID_PAGE_DESCRIPTION = "";
    private static final String VALID_PAGE_DESCRIPTION = "This is a simple description!!";


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

        // No whitespaces
        assertEquals(INDEX_FIRST_DASHBOARD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_DASHBOARD, ParserUtil.parseIndex("  1  "));
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
    public void parseDashboardName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDashboardName((String) null));
    }

    @Test
    public void parseDashboardName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDashboardName(INVALID_TASKNAME));
    }

    @Test
    public void parseDashboardName_validValueWithoutWhitespace_returnsName() throws Exception {
        DashboardName expectedName = new DashboardName(VALID_TASKNAME);
        assertEquals(expectedName, ParserUtil.parseDashboardName(VALID_TASKNAME));
    }

    @Test
    public void parseDashboardName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_TASKNAME + WHITESPACE;
        DashboardName expectedName = new DashboardName(VALID_TASKNAME);
        assertEquals(expectedName, ParserUtil.parseDashboardName(nameWithWhitespace));
    }

    //

    @Test
    public void parseTaskDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTaskDate((String) null));
    }

    @Test
    public void parseTaskDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTaskDate(INVALID_TASKDATE));
    }

    @Test
    public void parseTaskDate_validValueWithoutWhitespace_returnsName() throws Exception {
        TaskDate expectedDate = new TaskDate(VALID_TASKDATE);
        assertEquals(expectedDate.toString(), ParserUtil.parseTaskDate(VALID_TASKDATE).toString());
    }

    @Test
    public void parseTaskDate_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_TASKDATE + WHITESPACE;
        TaskDate expectedDate = new TaskDate(VALID_TASKDATE);
        assertEquals(expectedDate.toString(), ParserUtil.parseTaskDate(dateWithWhitespace).toString());
    }

    @Test
    public void parseRecipeName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRecipeName((String) null));
    }

    @Test
    public void parseRecipeName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRecipeName(INVALID_NAME));
    }

    @Test
    public void parseRecipeName_validValueWithoutWhitespace_returnsName() throws Exception {
        RecipeName expectedName = new RecipeName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseRecipeName(VALID_NAME));
    }

    @Test
    public void parseRecipeName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        RecipeName expectedName = new RecipeName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseRecipeName(nameWithWhitespace));
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
    public void parsePageType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePageType((String) null));
    }

    @Test
    public void parsePageType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePageType(INVALID_PAGE_TYPE));
    }

    @Test
    public void parsePageType_validValueWithoutWhitespace_returnsPageType() throws Exception {
        PageType expectedPageType = new PageType(VALID_PAGE_TYPE);
        assertEquals(expectedPageType, ParserUtil.parsePageType(VALID_PAGE_TYPE));
    }

    @Test
    public void parsePageType_validValueWithWhitespace_returnsTrimmedPageType() throws Exception {
        String pageTypeWithWhitespace = WHITESPACE + VALID_PAGE_TYPE + WHITESPACE;
        PageType expectedPageType = new PageType(VALID_PAGE_TYPE);
        assertEquals(expectedPageType, ParserUtil.parsePageType(pageTypeWithWhitespace));
    }

    @Test
    public void parsePageDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePageDescription((String) null));
    }

    @Test
    public void parsePageDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePageDescription(INVALID_PAGE_DESCRIPTION));
    }

    @Test
    public void parsePageDescription_validValueWithoutWhitespace_returnsPageDescription() throws Exception {
        PageDescription expectedPageDescription = new PageDescription(VALID_PAGE_DESCRIPTION);
        assertEquals(expectedPageDescription, ParserUtil.parsePageDescription(VALID_PAGE_DESCRIPTION));
    }

    @Test
    public void parsePageDescription_validValueWithWhitespace_returnsTrimmedPageDescription() throws Exception {
        String pageDescriptionWithWhitespace = WHITESPACE + VALID_PAGE_DESCRIPTION + WHITESPACE;
        PageDescription expectedPageDescription = new PageDescription(VALID_PAGE_DESCRIPTION);
        assertEquals(expectedPageDescription, ParserUtil.parsePageDescription(pageDescriptionWithWhitespace));
    }

    @Test
    public void parsePageTitleName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
    }

    @Test
    public void parsePageTitle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTitle(INVALID_PAGE_TITLE));
    }

    @Test
    public void parsePageTitle_validValueWithoutWhitespace_returnsTitle() throws Exception {
        Title expectedTitle = new Title(VALID_PAGE_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_PAGE_TITLE));
    }

    @Test
    public void parsePageTitle_validValueWithWhitespace_returnsTrimmedPageTitle() throws Exception {
        String titleWithWhitespace = WHITESPACE + VALID_PAGE_TITLE + WHITESPACE;
        Title expectedTitle = new Title(VALID_PAGE_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(titleWithWhitespace));
    }

    @Test
    public void parseImage_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseImage((String) null));
    }

    @Test
    public void parseImage_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseImage(INVALID_PAGE_IMAGE));
    }

    @Test
    public void parseImage_validValueWithoutWhitespace_returnsImage() throws Exception {
        Image expectedImage = new Image(VALID_PAGE_IMAGE);
        assertEquals(expectedImage, ParserUtil.parseImage(VALID_PAGE_IMAGE));
    }

    @Test
    public void parseImage_validValueWithWhitespace_returnsTrimmedImage() throws Exception {
        String imageWithWhitespace = WHITESPACE + VALID_PAGE_IMAGE + WHITESPACE;
        Image expectedImage = new Image(VALID_PAGE_IMAGE);
        assertEquals(expectedImage, ParserUtil.parseImage(imageWithWhitespace));
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

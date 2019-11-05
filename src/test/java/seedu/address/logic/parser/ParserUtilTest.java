package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.util.Date;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class,
            MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("t1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("  t1"));
    }

    @Test
    public void parseType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseType((String) null));
    }

    @Test
    public void parseType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseType(""));
    }

    @Test
    public void parseType_validValue_success() throws Exception {
        assertEquals("b", ParserUtil.parseType("b1"));
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
    public void parseNames_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNames(null));
    }

    @Test
    public void parseNames_collectionWithInvalidNames_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNames(Arrays.asList("John", "Doe!")));
    }

    @Test
    public void parseNames_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseNames(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseNames_collectionWithValidNames_throwsParseException() throws Exception {
        List<Name> validNames = ParserUtil.parseNames(Arrays.asList("John", "Doe"));
        assertEquals(validNames, ParserUtil.parseNames(Arrays.asList("John", "Doe")));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory(null));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_TAG));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_TAG_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_TAG_1));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Category expectedCategory = new Category(VALID_TAG_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(tagWithWhitespace));
    }

    @Test
    public void parseCategories_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategories(null));
    }

    @Test
    public void parseCategories_collectionWithInvalidCategories_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategories(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseCategories_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseCategories(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseCategories_collectionWithValidCategories_returnsCategorySet() throws Exception {
        Set<Category> actualCategorySet = ParserUtil.parseCategories(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Category> expectedCategorySet = new HashSet<Category>(Arrays.asList(new Category(VALID_TAG_1),
            new Category(VALID_TAG_2)));

        assertEquals(expectedCategorySet, actualCategorySet);
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_invalidDescription_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription("!loveyou!"));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description("milk");
        assertEquals(expectedDescription, ParserUtil.parseDescription("milk"));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        Description expectedDescription = new Description("milk");
        assertEquals(expectedDescription, ParserUtil.parseDescription(WHITESPACE + "milk" + WHITESPACE));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_invalidDate_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("21/03/2019"));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date("21032019");
        assertEquals(expectedDate, ParserUtil.parseDate("21032019"));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        Date expectedDate = new Date("19112019");
        assertEquals(expectedDate, ParserUtil.parseDate(WHITESPACE + "19112019" + WHITESPACE));
    }

    @Test
    public void parseShares_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseShares(null));
    }

    @Test
    public void parseShares_invalidShares_throwsParseException() {
        List<String> invalidShares = new ArrayList<>(Arrays.asList("hello", "world"));
        assertThrows(ParseException.class, () -> ParserUtil.parseShares(invalidShares));
    }

    @Test
    public void parseShares_validValueWithoutWhitespace_returnsList() throws Exception {
        List<String> validShares = new ArrayList<>(Arrays.asList("1", "2"));
        List<Integer> expectedValidShares = new ArrayList<>(Arrays.asList(1, 2));
        assertEquals(expectedValidShares, ParserUtil.parseShares(validShares));
    }

    @Test
    public void parseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount(null));
    }

    @Test
    public void parseAmount_invalidAmount_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount("$100.00"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount("100.005"));
    }

    @Test
    public void parseAmount_validPositiveValue_returnsAmount() throws Exception {
        Amount expectedPositiveAmount = new Amount(10.00);
        assertEquals(expectedPositiveAmount, ParserUtil.parseAmount("10.00"));
    }

    @Test
    public void parseAmount_validNegativeValue_returnsAmount() throws Exception {
        Amount expectedNegativeAmount = new Amount(-10.00);
        assertEquals(expectedNegativeAmount, ParserUtil.parseAmount("-10.00"));
    }

    @Test
    public void parseBudgetIndex_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBudgetIndex(null));
    }

    @Test
    public void parseBudgetIndex_invalidZeroValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBudgetIndex("0"));
    }

    @Test
    public void parseBudgetIndex_invalidNegativeValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBudgetIndex("-1"));
    }

    @Test
    public void parseBudgetIndex_validPositiveValue_returnsBudgetIndex() throws Exception {
        Index index = Index.fromOneBased(1);
        assertEquals(index, ParserUtil.parseBudgetIndex("1"));
    }
}

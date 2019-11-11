package seedu.ichifund.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

public class ParserUtilTest {
    public static final String INVALID_DESCRIPTION = "!?";
    public static final String INVALID_AMOUNT_FORMAT = "1.9";
    public static final String INVALID_AMOUNT_NEGATIVE = "-50.00";
    public static final String INVALID_AMOUNT_ZERO = "0.00";
    public static final String INVALID_CATEGORY = "?!";
    public static final String INVALID_DAY = "32";
    public static final String INVALID_MONTH = "13";
    public static final String INVALID_YEAR = "99";
    public static final String INVALID_TRANSACTION_TYPE = "hi";

    public static final String VALID_DESCRIPTION = "Lunch";
    public static final String VALID_AMOUNT = "100.20";
    public static final String VALID_CATEGORY = "food";
    public static final String VALID_DAY = "12";
    public static final String VALID_MONTH = "6";
    public static final String VALID_YEAR = "4999";
    public static final String VALID_TRANSACTION_TYPE = "exp";

    public static final String SPECIAL_ALL_CATEGORY = "!all";
    public static final String SPECIAL_ALL_TRANSACTION_TYPE = "!all";

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
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount((String) null));
    }

    @Test
    public void parseAmount_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT_FORMAT));
    }

    @Test
    public void parseAmount_validValueWithoutWhitespace_returnsAmount() throws Exception {
        Amount expectedAmount = new Amount(VALID_AMOUNT);
        assertEquals(expectedAmount, ParserUtil.parseAmount(VALID_AMOUNT));
    }

    @Test
    public void parseAmount_validValueWithWhitespace_returnsTrimmedAmount() throws Exception {
        String amountWithWhitespace = WHITESPACE + VALID_AMOUNT + WHITESPACE;
        Amount expectedAmount = new Amount(VALID_AMOUNT);
        assertEquals(expectedAmount, ParserUtil.parseAmount(amountWithWhitespace));
    }

    @Test
    public void parsePositiveAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePositiveAmount((String) null));
    }

    @Test
    public void parsePositiveAmount_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePositiveAmount(INVALID_AMOUNT_FORMAT));
        assertThrows(ParseException.class, () -> ParserUtil.parsePositiveAmount(INVALID_AMOUNT_NEGATIVE));
        assertThrows(ParseException.class, () -> ParserUtil.parsePositiveAmount(INVALID_AMOUNT_ZERO));
    }

    @Test
    public void parsePositiveAmount_validValueWithoutWhitespace_returnsPositiveAmount() throws Exception {
        Amount expectedPositiveAmount = new Amount(VALID_AMOUNT);
        assertEquals(expectedPositiveAmount, ParserUtil.parsePositiveAmount(VALID_AMOUNT));
    }

    @Test
    public void parsePositiveAmount_validValueWithWhitespace_returnsTrimmedPositiveAmount() throws Exception {
        String positiveAmountWithWhitespace = WHITESPACE + VALID_AMOUNT + WHITESPACE;
        Amount expectedPositiveAmount = new Amount(VALID_AMOUNT);
        assertEquals(expectedPositiveAmount, ParserUtil.parsePositiveAmount(positiveAmountWithWhitespace));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory((String) null));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(SPECIAL_ALL_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY + WHITESPACE;
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
    }

    @Test
    public void parseTransactionType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTransactionType((String) null));
    }

    @Test
    public void parseTransactionType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionType(INVALID_TRANSACTION_TYPE));
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionType(SPECIAL_ALL_TRANSACTION_TYPE));
    }

    @Test
    public void parseTransactionType_validValueWithoutWhitespace_returnsTransactionType() throws Exception {
        TransactionType expectedTransactionType = new TransactionType(VALID_TRANSACTION_TYPE);
        assertEquals(expectedTransactionType, ParserUtil.parseTransactionType(VALID_TRANSACTION_TYPE));
    }

    @Test
    public void parseTransactionType_validValueWithWhitespace_returnsTrimmedTransactionType() throws Exception {
        String transactionTypeWithWhitespace = WHITESPACE + VALID_TRANSACTION_TYPE + WHITESPACE;
        TransactionType expectedTransactionType = new TransactionType(VALID_TRANSACTION_TYPE);
        assertEquals(expectedTransactionType, ParserUtil.parseTransactionType(transactionTypeWithWhitespace));
    }

    @Test
    public void parseDay_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDay((String) null));
    }

    @Test
    public void parseDay_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay(INVALID_DAY));
    }

    @Test
    public void parseDay_validValueWithoutWhitespace_returnsDay() throws Exception {
        Day expectedDay = new Day(VALID_DAY);
        assertEquals(expectedDay, ParserUtil.parseDay(VALID_DAY));
    }

    @Test
    public void parseDay_validValueWithWhitespace_returnsTrimmedDay() throws Exception {
        String dayWithWhitespace = WHITESPACE + VALID_DAY + WHITESPACE;
        Day expectedDay = new Day(VALID_DAY);
        assertEquals(expectedDay, ParserUtil.parseDay(dayWithWhitespace));
    }

    @Test
    public void parseMonth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMonth((String) null));
    }

    @Test
    public void parseMonth_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMonth(INVALID_MONTH));
    }

    @Test
    public void parseMonth_validValueWithoutWhitespace_returnsMonth() throws Exception {
        Month expectedMonth = new Month(VALID_MONTH);
        assertEquals(expectedMonth, ParserUtil.parseMonth(VALID_MONTH));
    }

    @Test
    public void parseMonth_validValueWithWhitespace_returnsTrimmedMonth() throws Exception {
        String monthWithWhitespace = WHITESPACE + VALID_MONTH + WHITESPACE;
        Month expectedMonth = new Month(VALID_MONTH);
        assertEquals(expectedMonth, ParserUtil.parseMonth(monthWithWhitespace));
    }

    @Test
    public void parseYear_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseYear((String) null));
    }

    @Test
    public void parseYear_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithoutWhitespace_returnsYear() throws Exception {
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedYear() throws Exception {
        String yearWithWhitespace = WHITESPACE + VALID_YEAR + WHITESPACE;
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(yearWithWhitespace));
    }

    @Test
    public void parseCategoryWithAll_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategoryWithAll((String) null));
    }

    @Test
    public void parseCategoryWithAll_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategoryWithAll(INVALID_CATEGORY));
    }

    @Test
    public void parseCategoryWithAll_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategoryWithAll(VALID_CATEGORY));
        assertEquals(Category.CATEGORY_ALL, ParserUtil.parseCategoryWithAll(SPECIAL_ALL_CATEGORY));
    }

    @Test
    public void parseCategoryWithAll_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY + WHITESPACE;
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategoryWithAll(categoryWithWhitespace));
    }

    @Test
    public void parseTransactionTypeWithAll_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTransactionTypeWithAll((String) null));
    }

    @Test
    public void parseTransactionTypeWithAll_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionTypeWithAll(INVALID_TRANSACTION_TYPE));
    }

    @Test
    public void parseTransactionTypeWithAll_validValueWithoutWhitespace_returnsTransactionType() throws Exception {
        TransactionType expectedTransactionType = new TransactionType(VALID_TRANSACTION_TYPE);
        assertEquals(expectedTransactionType, ParserUtil.parseTransactionTypeWithAll(VALID_TRANSACTION_TYPE));
        assertEquals(TransactionType.TRANSACTION_TYPE_ALL,
                ParserUtil.parseTransactionTypeWithAll(SPECIAL_ALL_TRANSACTION_TYPE));
    }

    @Test
    public void parseTransactionTypeWithAll_validValueWithWhitespace_returnsTrimmedTransactionType() throws Exception {
        String transactionTypeWithWhitespace = WHITESPACE + VALID_TRANSACTION_TYPE + WHITESPACE;
        TransactionType expectedTransactionType = new TransactionType(VALID_TRANSACTION_TYPE);
        assertEquals(expectedTransactionType, ParserUtil.parseTransactionTypeWithAll(transactionTypeWithWhitespace));
    }
}

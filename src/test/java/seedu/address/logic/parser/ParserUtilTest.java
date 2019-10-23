package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Alias;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Price;

public class ParserUtilTest {
    private static final String INVALID_DESCRIPTION = "J4()\\|\\\\|nyVV@1k3r";
    private static final String INVALID_PRICE = ",1234";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_DESCRIPTION = "Johnny Walker";
    private static final String VALID_PRICE = "12.3456";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    private static final String VALID_ALIAS_NAME_1 = "name";
    private static final String VALID_ALIAS_INPUT_1 = "input";
    private static final String INVALID_ALIAS_NAME_1 = "";
    private static final String INVALID_ALIAS_NAME_2 = "@#*j";
    private static final String INVALID_ALIAS_INPUT_1 = "";

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
        assertEquals(INDEX_FIRST_EXPENSE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_EXPENSE, ParserUtil.parseIndex("  1  "));
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
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice((String) null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(priceWithWhitespace));
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
    public void parseCategory_validValueWithoutWhitespace_returnsTag() throws Exception {
        Category expectedCategory = new Category(VALID_TAG_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_TAG_1));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Category expectedCategory = new Category(VALID_TAG_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(tagWithWhitespace));
    }

    @Test
    void parseAlias_validInput_returnsAlias() throws Exception {
        Alias expectedAlias = new Alias(VALID_ALIAS_NAME_1, VALID_ALIAS_INPUT_1);

        assertEquals(ParserUtil.parseAlias(VALID_ALIAS_NAME_1, VALID_ALIAS_INPUT_1), expectedAlias);
    }

    @Test
    void parseAlias_invalidInput_throwsParseException() {
        // non alphanumeric name
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias(INVALID_ALIAS_NAME_1, VALID_ALIAS_INPUT_1));

        // space in name
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias(
                VALID_ALIAS_NAME_1 + WHITESPACE + VALID_ALIAS_NAME_1,
                VALID_ALIAS_INPUT_1));

        // empty name
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias(
                WHITESPACE,
                VALID_ALIAS_INPUT_1));

        // white space input
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias(
                VALID_ALIAS_NAME_1,
                WHITESPACE));

        // both invalid
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias(
                INVALID_ALIAS_NAME_2,
                WHITESPACE));

    }
}

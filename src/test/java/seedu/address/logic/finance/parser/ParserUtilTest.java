package seedu.address.logic.finance.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOGENTRY;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.GroupByAttr;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;

public class ParserUtilTest {

    private static final String INVALID_AMOUNT = "0.00";
    private static final String INVALID_TDATE = "10/10/2019";
    private static final String INVALID_DESC = " ";
    private static final String INVALID_TMET = "c@sh";
    private static final String INVALID_PLACE = " ";
    private static final String INVALID_FROM = " ";
    private static final String INVALID_TO = " ";
    private static final String INVALID_CAT_FOOD = "food cat";
    private static final String INVALID_CAT_PRESENT = "pre sent";
    private static final String INVALID_GROUPBYATTR = " ";
    private static final String INVALID_SUMBYATTR = " ";
    private static final String INVALID_DUR = "-7";

    private static final String VALID_AMOUNT = "2.80";
    private static final String VALID_TDATE = "10-10-2019";
    private static final String VALID_DESC = "Yong Tau Foo";
    private static final String VALID_TMET = "cash";
    private static final String VALID_PLACE = "Frontier";
    private static final String VALID_FROM = "YTF Auntie";
    private static final String VALID_TO = "Me";
    private static final String VALID_CAT_FOOD = "food";
    private static final String VALID_CAT_PRESENT = "present";
    private static final String VALID_GROUPBYATTR = "entrytype";
    private static final String VALID_SUMBYATTR = "freq";
    private static final String VALID_DUR = "7";

    private static final String WHITESPACE = "  ";

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
        assertEquals(INDEX_FIRST_LOGENTRY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_LOGENTRY, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount((String) null));
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
    public void parseTDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTransactionDate((String) null));
    }

    @Test
    public void parseTransactionDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionDate(INVALID_TDATE));
    }

    @Test
    public void parseTransactionDate_validValueWithoutWhitespace_returnsTransactionDate() throws Exception {
        TransactionDate expectedTDate = new TransactionDate(VALID_TDATE);
        assertEquals(expectedTDate, ParserUtil.parseTransactionDate(VALID_TDATE));
    }

    @Test
    public void parseTransactionDate_validValueWithWhitespace_returnsTrimmedTransactionDate() throws Exception {
        String tDateWithWhitespace = WHITESPACE + VALID_TDATE + WHITESPACE;
        TransactionDate expectedTDate = new TransactionDate(VALID_TDATE);
        assertEquals(expectedTDate, ParserUtil.parseTransactionDate(tDateWithWhitespace));
    }

    @Test
    public void parseTransactionMethod_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTransactionMethod((String) null));
    }

    @Test
    public void parseTransactionMethod_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionMethod(INVALID_TMET));
    }

    @Test
    public void parseTransactionMethod_validValueWithoutWhitespace_returnsTransactionMethod() throws Exception {
        TransactionMethod expectedTMet = new TransactionMethod(VALID_TMET);
        assertEquals(expectedTMet, ParserUtil.parseTransactionMethod(VALID_TMET));
    }

    @Test
    public void parseTransactionMethod_validValueWithWhitespace_returnsTrimmedTransactionMethod() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_TMET + WHITESPACE;
        TransactionMethod expectedAddress = new TransactionMethod(VALID_TMET);
        assertEquals(expectedAddress, ParserUtil.parseTransactionMethod(addressWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedEmail = new Description(VALID_DESC);
        assertEquals(expectedEmail, ParserUtil.parseDescription(VALID_DESC));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESC + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESC);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseCat_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory(null));
    }

    @Test
    public void parseCat_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CAT_FOOD));
    }

    @Test
    public void parseCat_validValueWithoutWhitespace_returnsCat() throws Exception {
        Category expectedCat = new Category(VALID_CAT_FOOD);
        assertEquals(expectedCat, ParserUtil.parseCategory(VALID_CAT_FOOD));
    }

    @Test
    public void parseCat_validValueWithWhitespace_returnsTrimmedCat() throws Exception {
        String catWithWhitespace = WHITESPACE + VALID_CAT_FOOD + WHITESPACE;
        Category expectedCat = new Category(VALID_CAT_FOOD);
        assertEquals(expectedCat, ParserUtil.parseCategory(catWithWhitespace));
    }

    @Test
    public void parseCats_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategories(null));
    }

    @Test
    public void parseCats_collectionWithInvalidCats_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseCategories(Arrays.asList(INVALID_CAT_FOOD, INVALID_CAT_PRESENT)));
    }

    @Test
    public void parseCats_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseCategories(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseCats_collectionWithValidCats_returnsCatSet() throws Exception {
        Set<Category> actualTagSet = ParserUtil.parseCategories(Arrays.asList(VALID_CAT_FOOD, VALID_CAT_PRESENT));
        Set<Category> expectedTagSet =
            new HashSet<>(Arrays.asList(new Category(VALID_CAT_FOOD), new Category(VALID_CAT_PRESENT)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parsePlace_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePlace((String) null));
    }

    @Test
    public void parsePlace_validValueWithoutWhitespace_returnsPlace() throws Exception {
        Place expectedPlace = new Place(VALID_PLACE);
        assertEquals(expectedPlace, ParserUtil.parsePlace(VALID_PLACE));
    }

    @Test
    public void parsePlace_validValueWithWhitespace_returnsTrimmedPlace() throws Exception {
        String placeWithWhitespace = WHITESPACE + VALID_PLACE + WHITESPACE;
        Place expectedPlace = new Place(VALID_PLACE);
        assertEquals(expectedPlace, ParserUtil.parsePlace(placeWithWhitespace));
    }

    @Test
    public void parsePerson_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePerson((String) null));
    }

    @Test
    public void parsePerson_validValueWithoutWhitespace_returnsPerson() throws Exception {
        Person expectedPerson = new Person(VALID_FROM);
        assertEquals(expectedPerson, ParserUtil.parsePerson(VALID_FROM));
    }

    @Test
    public void parsePerson_validValueWithWhitespace_returnsTrimmedPerson() throws Exception {
        String personWithWhitespace = WHITESPACE + VALID_TO + WHITESPACE;
        Person expectedPerson = new Person(VALID_TO);
        assertEquals(expectedPerson, ParserUtil.parsePerson(personWithWhitespace));
    }

    @Test
    public void parseGroupByAttr_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroupByAttr((String) null));
    }

    @Test
    public void parseGroupByAttr_validValueWithoutWhitespace_returnsGroupByAttr() throws Exception {
        GroupByAttr expectedGroupByAttr = new GroupByAttr(VALID_GROUPBYATTR);
        assertEquals(expectedGroupByAttr, ParserUtil.parseGroupByAttr(VALID_GROUPBYATTR));
    }

    @Test
    public void parseGroupByAttr_validValueWithWhitespace_returnsTrimmedGroupByAttr() throws Exception {
        String groupByAttrWithWhitespace = WHITESPACE + VALID_GROUPBYATTR + WHITESPACE;
        GroupByAttr expectedGroupByAttr = new GroupByAttr(VALID_GROUPBYATTR);
        assertEquals(expectedGroupByAttr, ParserUtil.parseGroupByAttr(groupByAttrWithWhitespace));
    }

    @Test
    public void parseGroupByAttr_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGroupByAttr(INVALID_GROUPBYATTR));
    }

    @Test
    public void parseSummariseAttr_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSummariseAttr((String) null));
    }

    @Test
    public void parseSummariseAttr_validValueWithoutWhitespace_returnsSummariseAttr() throws Exception {
        String expectedSummariseByAttr = VALID_SUMBYATTR;
        assertEquals(expectedSummariseByAttr, ParserUtil.parseSummariseAttr(VALID_SUMBYATTR));
    }

    @Test
    public void parseSummariseAttr_validValueWithWhitespace_returnsTrimmedSummariseAttr() throws Exception {
        String sumByAttrWithWhitespace = WHITESPACE + VALID_SUMBYATTR + WHITESPACE;
        String expectedSumByAttr = VALID_SUMBYATTR;
        assertEquals(expectedSumByAttr, ParserUtil.parseSummariseAttr(sumByAttrWithWhitespace));
    }

    @Test
    public void parseSummariseAttr_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSummariseAttr(INVALID_SUMBYATTR));
    }

    @Test
    public void parseDur_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDur((String) null));
    }

    @Test
    public void parseDur_validValueWithoutWhitespace_returnsDur() throws Exception {
        long numMilliSecondsInADay = 1000 * 60 * 60 * 24;
        long expectedDur = Integer.parseInt(VALID_DUR) * numMilliSecondsInADay;
        assertEquals(expectedDur, ParserUtil.parseDur(VALID_DUR));
    }

    @Test
    public void parseDur_validValueWithWhitespace_returnsTrimmedDur() throws Exception {
        String durWithWhitespace = WHITESPACE + VALID_DUR + WHITESPACE;
        long numMilliSecondsInADay = 1000 * 60 * 60 * 24;
        long expectedDur = Integer.parseInt(VALID_DUR) * numMilliSecondsInADay;
        assertEquals(expectedDur, ParserUtil.parseDur(durWithWhitespace));
    }

    @Test
    public void parseDur_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDur(INVALID_DUR));
    }

}

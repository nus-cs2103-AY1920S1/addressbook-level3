package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.EmployeeAddress;
import seedu.address.model.employee.EmployeeEmail;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDateTimeMap;
import seedu.address.model.event.EventDayTime;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_VENUE = "sTA@BUCKS";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
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
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
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
        EmployeeName expectedEmployeeName = new EmployeeName(VALID_NAME);
        assertEquals(expectedEmployeeName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        EmployeeName expectedEmployeeName = new EmployeeName(VALID_NAME);
        assertEquals(expectedEmployeeName, ParserUtil.parseName(nameWithWhitespace));
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
        EmployeePhone expectedEmployeePhone = new EmployeePhone(VALID_PHONE);
        assertEquals(expectedEmployeePhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        EmployeePhone expectedEmployeePhone = new EmployeePhone(VALID_PHONE);
        assertEquals(expectedEmployeePhone, ParserUtil.parsePhone(phoneWithWhitespace));
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
        EmployeeAddress expectedEmployeeAddress = new EmployeeAddress(VALID_ADDRESS);
        assertEquals(expectedEmployeeAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        EmployeeAddress expectedEmployeeAddress = new EmployeeAddress(VALID_ADDRESS);
        assertEquals(expectedEmployeeAddress, ParserUtil.parseAddress(addressWithWhitespace));
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
        EmployeeEmail expectedEmployeeEmail = new EmployeeEmail(VALID_EMAIL);
        assertEquals(expectedEmployeeEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        EmployeeEmail expectedEmployeeEmail = new EmployeeEmail(VALID_EMAIL);
        assertEquals(expectedEmployeeEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseEventName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventName(INVALID_NAME));
    }

    @Test
    public void parseEventDateTimeMap() throws ParseException {
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("20102019")); //Only date, no time
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("20102019-1000-2000")); //Wrong format, should have colon
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("30102019:1000-2000")); //invalid date
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("20102019:5000-2000")); //invalid time
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("20102019:1000-2000;20102019:1000-2000")); //Wrong delimiter

        EventDateTimeMap testMap = new EventDateTimeMap();
        EventDate dateOne = new EventDate(LocalDate.of(2019, 10, 20));
        EventDayTime timeOne = new EventDayTime(LocalTime.of(10, 0), LocalTime.of(20, 0));
        testMap.mapDateTime(dateOne, timeOne);
        assertEquals(ParserUtil.parseEventDateTimeMap("20/10/2019:1000-2000"), testMap);

        EventDate dateTwo = new EventDate(LocalDate.of(2019, 10, 21));
        EventDayTime timeTwo = new EventDayTime(LocalTime.of(17, 0), LocalTime.of(21, 0));
        testMap.mapDateTime(dateTwo, timeTwo);
        assertEquals(ParserUtil.parseEventDateTimeMap("20/10/2019:1000-2000,21/10/2019:1700-2100"), testMap);
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsEventDate() throws Exception {
        EventDate dateTestOne = new EventDate(LocalDate.of(2019, 10, 20));
        assertEquals(ParserUtil.parseDate("20/10/2019"), dateTestOne);

        EventDate dateTestTwo = new EventDate(LocalDate.of(2019, 10, 21));
        assertEquals(ParserUtil.parseDate("21/10/2019"), dateTestTwo);
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsEventDate() throws Exception {
        EventDate dateTestOne = new EventDate(LocalDate.of(2019, 10, 20));
        assertEquals(ParserUtil.parseDate("20/10/2019 "), dateTestOne);

        EventDate dateTestTwo = new EventDate(LocalDate.of(2019, 10, 21));
        assertEquals(ParserUtil.parseDate(" 21/10/2019"), dateTestTwo);
    }

    @Test
    public void parseDate_invalidFormat() {
        //Format is not in dd/MM/yyyy format
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("20102019"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("20191020"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("21 August 2019"));
    }

    @Test
    public void parseDate_invalidValue() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("01/9/2019"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("1/10/2019"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("date"));
    }
}

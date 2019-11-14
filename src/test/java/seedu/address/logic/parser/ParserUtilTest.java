package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEventDates.OCT_20_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_21_2019;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_0800_TO_1230;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_0800_TO_1800;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
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
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    //Common Invalid Fields
    private static final String INVALID_NAME = "R@chel"; //Both Employee and Events

    //Invalid Employee Fields
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    //Invalid Event Fields
    private static final String INVALID_EVENT_VENUE = "  ";
    private static final String INVALID_EVENT_MANPOWER_NEEDED = "200";
    private static final String INVALID_EVENT_DATE = "30102019";
    private static final String INVALID_EVENT_DATE_TIME_MAP = "30/10/2019:1000-2300";
    private static final String INVALID_MANPOWER_ALLOCATED_LIST = "001, 002, 003";

    //Common Valid Fields
    private static final String VALID_NAME = "Rachel Walker";

    //Valid Employees Fields
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    //Valid Events Fields
    private static final String VALID_EVENT_VENUE = "Kent Ridge";
    private static final String VALID_EVENT_MANPOWER_NEEDED = "5";
    private static final String VALID_EVENT_DATE = "30/10/2019";
    private static final String VALID_EVENT_DAYTIME = "0800-1800";
    private static final String VALID_EVENT_DATE_TIME_MAP = "30102019:1000-1500";
    private static final String VALID_MANPOWER_ALLOCATED_LIST = "001 002 003";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        EmployeeName expectedEmployeeName = new EmployeeName(VALID_NAME);
        assertEquals(expectedEmployeeName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        EmployeeName expectedEmployeeName = new EmployeeName(VALID_NAME);
        assertEquals(expectedEmployeeName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        EmployeePhone expectedEmployeePhone = new EmployeePhone(VALID_PHONE);
        assertEquals(expectedEmployeePhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        EmployeePhone expectedEmployeePhone = new EmployeePhone(VALID_PHONE);
        assertEquals(expectedEmployeePhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        EmployeeAddress expectedEmployeeAddress = new EmployeeAddress(VALID_ADDRESS);
        assertEquals(expectedEmployeeAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        EmployeeAddress expectedEmployeeAddress = new EmployeeAddress(VALID_ADDRESS);
        assertEquals(expectedEmployeeAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        EmployeeEmail expectedEmployeeEmail = new EmployeeEmail(VALID_EMAIL);
        assertEquals(expectedEmployeeEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        EmployeeEmail expectedEmployeeEmail = new EmployeeEmail(VALID_EMAIL);
        assertEquals(expectedEmployeeEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    void parseEventName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEventName((String) null));
    }

    @Test
    void parseEventName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventName(INVALID_NAME));
    }

    @Test
    void parseEventName_validValueWithoutWhitespace_returnsEventName() throws ParseException {
        EventName expectedEventName = new EventName(VALID_NAME);
        assertEquals(expectedEventName, ParserUtil.parseEventName(VALID_NAME));
    }

    @Test
    void parseEventName_validValueWithWhitespace_returnsEventName() throws ParseException {
        String eventNameWithWhitesppace = WHITESPACE + VALID_NAME + WHITESPACE;
        EventName expectedEventName = new EventName(VALID_NAME);
        assertEquals(expectedEventName, ParserUtil.parseEventName(eventNameWithWhitesppace));
    }

    @Test
    void parseEventVenue_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseVenue((String) null));
    }

    @Test
    void parseEventVenue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseVenue(INVALID_EVENT_VENUE));
    }

    @Test
    void parseEventVenue_validValueWithoutWhitespace_returnsEventVenue() throws ParseException {
        EventVenue expectedEventVenue = new EventVenue(VALID_EVENT_VENUE);
        assertEquals(expectedEventVenue, ParserUtil.parseVenue(VALID_EVENT_VENUE));
    }

    @Test
    void parseEventVenue_validValueWithWhitespace_returnsEventVenue() throws ParseException {
        String eventVenueWithWhitesppace = WHITESPACE + VALID_EVENT_VENUE + WHITESPACE;
        EventVenue expectedEventVenue = new EventVenue(VALID_EVENT_VENUE);
        assertEquals(expectedEventVenue, ParserUtil.parseVenue(eventVenueWithWhitesppace));
    }

    @Test
    void parseEventDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEventDate((String) null));
    }

    @Test
    void parseEventDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EVENT_DATE));
    }

    @Test
    void parseEventDate_validValueWithoutWhitespace_returnsEventDate() throws Exception {
        EventDate expectedEventDate = new EventDate(LocalDate.of(2019, 10, 30));
        assertEquals(ParserUtil.parseEventDate(VALID_EVENT_DATE), expectedEventDate);
    }

    @Test
    void parseEventDate_validValueWithWhitespace_returnsEventDate() throws Exception {
        String eventDateWithWhitespace = WHITESPACE + VALID_EVENT_DATE + WHITESPACE;
        EventDate expectedEventDate = new EventDate(LocalDate.of(2019, 10, 30));
        assertEquals(ParserUtil.parseEventDate(eventDateWithWhitespace), expectedEventDate);
    }

    @Test
    void parseDate_invalidFormat() {
        //Format is not in dd/MM/yyyy format
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("20102019"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("20191020"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("21 August 2019"));
    }

    @Test
    void parseDate_invalidValue() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("01/9/2019"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("1/10/2019"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("date"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("31/02/2019")); //not a calendar date
        assertThrows(ParseException.class, () -> ParserUtil.parseAnyDate("20/02/1809")); //not a calendar date
    }

    @Test
    void parseTimePeriod_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTimePeriod((String) null));
    }

    @Test
    void parseTimePeriod_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTimePeriod("09:30-10:30"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTimePeriod("0930-530pm"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTimePeriod("530-1030"));
    }

    @Test
    void parseTimePeriod_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTimePeriod("1000-2400"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTimePeriod("1000-0900"));
    }

    @Test
    void parseTimePeriod_validValueWithoutWhitespace_returnsEventDayTime() throws Exception {
        assertEquals(TIME_0800_TO_1800, ParserUtil.parseTimePeriod(VALID_EVENT_DAYTIME));
    }

    @Test
    void parseTimePeriod_validValueWithWhitespace_returnsEventDayTime() throws Exception {
        String eventDayTimeWithWhitespace = WHITESPACE + VALID_EVENT_DAYTIME + WHITESPACE;
        assertEquals(TIME_0800_TO_1800, ParserUtil.parseTimePeriod(eventDayTimeWithWhitespace));
    }

    @Test
    void parseManpowerNeeded_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseManpowerNeeded((String) null));
    }

    @Test
    void parseManpowerNeeded_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseManpowerNeeded(INVALID_EVENT_MANPOWER_NEEDED));
    }

    @Test
    void parseManpowerNeeded_validValueWithoutWhitespace_returnsEventManpowerNeeded() throws Exception {
        EventManpowerNeeded expectedEventManpowerNeeded = new EventManpowerNeeded(VALID_EVENT_MANPOWER_NEEDED);
        assertEquals(expectedEventManpowerNeeded, ParserUtil.parseManpowerNeeded(VALID_EVENT_MANPOWER_NEEDED));
    }

    @Test
    void parseManpowerNeeded_validValueWithWhitespace_returnsEventManpowerNeeded() throws Exception {
        String expectedEventManpowerNeededWithWhitespace = WHITESPACE + VALID_EVENT_MANPOWER_NEEDED + WHITESPACE;
        EventManpowerNeeded expectedEventManpowerNeeded = new EventManpowerNeeded(VALID_EVENT_MANPOWER_NEEDED);
        assertEquals(expectedEventManpowerNeeded,
                ParserUtil.parseManpowerNeeded(expectedEventManpowerNeededWithWhitespace));
    }

    @Test
    void parseEventDateTimeMap_invalidFormat() {
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("20102019")); //Only date, no time
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("20102019-1000-2000")); //Wrong format, should have colon
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("30102019:1000-2000")); //Should have slash delimiter
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("20102019:1000-2000;20102019:1000-2000")); //Wrong delimiter
    }

    @Test
    void parseEventDateTimeMap_invalidValues() {
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("31/02/2019:1000-2000")); //Invalid Calendar Date
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("30/10/2019:5000-2000")); //Invalid Time
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("21/02/2019:1000-0900")); //StartTime > EndTime
        assertThrows(ParseException.class, ()
            -> ParserUtil.parseEventDateTimeMap("30/10/1900:0500-2000")); //Too Long ago

    }

    @Test
    void parseEventDateTimeMap_validValues() throws ParseException {
        EventDateTimeMap testMap = new EventDateTimeMap();
        testMap.mapDateTime(OCT_20_2019, TIME_0800_TO_1800);
        assertEquals(ParserUtil.parseEventDateTimeMap("20/10/2019:0800-1800"), testMap);

        testMap.mapDateTime(OCT_21_2019, TIME_0800_TO_1230);
        assertEquals(ParserUtil.parseEventDateTimeMap("20/10/2019:0800-1800,21/10/2019:0800-1230"), testMap);
    }

}

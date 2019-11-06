package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.EMPTY_VENUE;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.EMPTY_VENUE_TIME_SLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.INVALID_DATE;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.INVALID_TIMING;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMING1;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

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

    //============================ parseName ======================================

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

    //============================ parseGroupName ======================================

    @Test
    public void parseGroupName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroupName((String) null));
    }

    @Test
    public void parseGroupName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName("asd//asd"));
    }

    @Test
    public void parseGroupName_validValueWithoutWhitespace_returnsName() throws Exception {
        assertTrue(GROUP_NAME1.equals(ParserUtil.parseGroupName(GROUP_NAME1.toString())));
    }

    @Test
    public void parseGroupName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        assertTrue(GROUP_NAME1.equals(ParserUtil.parseGroupName(GROUP_NAME1.toString() + " ")));
    }

    //============================ parsePhone ======================================

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

    //============================ parseAddress ======================================

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

    //============================ parseEmail ======================================

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

    //============================ parseTag ======================================

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
    public void parseTag_emptyTag() throws ParseException {
        assertEquals(null, ParserUtil.parseTag(""));
    }

    //============================ parseTags ======================================

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

    //============================ parseRemark ======================================

    @Test
    public void parseRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark(null));
    }

    @Test
    public void parseRemark_validValue() throws Exception {
        assertEquals(new Remark("remark"), ParserUtil.parseRemark("remark"));
    }

    //============================ parseRole ======================================

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseRole_validValue() throws Exception {
        assertTrue(new Role("role").equals(ParserUtil.parseRole("role")));
    }

    //============================ parseGroupDescription ======================================

    @Test
    public void parseGroupDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroupDescription(null));
    }

    @Test
    public void parseGroupDescription_validValue() throws Exception {
        assertTrue(new GroupDescription("description").equals(ParserUtil.parseGroupDescription("description")));
    }

    @Test
    public void parseGroupDescription_invalidValue() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseGroupDescription("a/sp"));
    }

    //============================ parseTimeSlot ======================================

    @Test
    public void parseTimeslot_invalidFormat() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTimeslot("a/sp"));

        try {
            ParserUtil.parseTimeslot("a/sp");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE),
                    e.getMessage());
        }
    }

    @Test
    public void parseTimeslot_validFormat() throws ParseException {
        assertTrue(TIME_SLOT1.equals(ParserUtil.parseTimeslot(TIMING1)));
    }

    @Test
    public void parseTimeslot_emptyVenue() throws ParseException {
        assertTrue(EMPTY_VENUE_TIME_SLOT1.equals(ParserUtil.parseTimeslot(EMPTY_VENUE)));
    }

    @Test
    public void parseTimeslot_invalidDate() throws ParseException {
        try {
            ParserUtil.parseTimeslot(INVALID_DATE);
        } catch (ParseException e) {
            assertEquals(AddEventCommand.MESSAGE_WRONG_TIMINGS,
                    e.getMessage());
        }
    }

    @Test
    public void parseTimeslot_invalidTimings() throws ParseException {
        try {
            ParserUtil.parseTimeslot(INVALID_TIMING);
        } catch (ParseException e) {
            assertEquals("End time cannot be before start time.",
                    e.getMessage());
        }
    }

    //============================ parseEventName ======================================

    @Test
    public void parseEventName_invalidFormat() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventName("a/sp"));

        try {
            ParserUtil.parseEventName("a/sp");
        } catch (ParseException e) {
            assertEquals("An event name should only contain alphanumeric characters and spaces, "
                            + "and it should not be blank",
                    e.getMessage());
        }
    }

    @Test
    public void parseEventName_validFormat() throws ParseException {
        assertTrue(EVENT_NAME1.equals(ParserUtil.parseEventName(EVENT_NAME1 + " ")));
    }

    //============================ parseWeek ======================================

    @Test
    public void parseWeek_invalidFormat() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek("7"));

        try {
            ParserUtil.parseEventName("7");
        } catch (ParseException e) {
            assertEquals("A week has to be an integer from 1 to 4.",
                    e.getMessage());
        }
    }

    @Test
    public void parseWeek_notNumber() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek("asd"));

        try {
            ParserUtil.parseEventName("asd");
        } catch (ParseException e) {
            assertEquals("A week has to be an integer from 1 to 4.",
                    e.getMessage());
        }
    }

    @Test
    public void parseWeek_valid() throws ParseException {
        assertEquals(3, ParserUtil.parseWeek("4"));
    }

    //============================ parseDay ======================================

    @Test
    public void parseDay_invalidFormat() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay("88"));

        try {
            ParserUtil.parseDay("88");
        } catch (ParseException e) {
            assertEquals("A day has to be an integer between 1 and 7",
                    e.getMessage());
        }
    }

    @Test
    public void parseDay_notNumber() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay("asd"));

        try {
            ParserUtil.parseDay("asd");
        } catch (ParseException e) {
            assertEquals("A day has to be an integer between 1 and 7",
                    e.getMessage());
        }
    }

    @Test
    public void parseDay_valid() throws ParseException {
        assertEquals(3, ParserUtil.parseDay("3"));
    }

    //============================ parseId ======================================

    @Test
    public void parseId_notNumber() {
        assertThrows(ParseException.class, () -> ParserUtil.parseId("asd"));

        try {
            ParserUtil.parseId("asd");
        } catch (ParseException e) {
            assertEquals("An id has to be an integer",
                    e.getMessage());
        }
    }

    @Test
    public void parseId_valid() throws ParseException {
        assertEquals(3, ParserUtil.parseId("3"));
    }

}

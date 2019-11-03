package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;
import seedu.address.model.exceptions.ReferenceIdCannotChangeClassificationException;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.PersonReferenceId;
import seedu.address.model.person.parameters.Phone;
import seedu.address.model.person.parameters.Tag;

public class ParserUtilTest {
    private static final String INVALID_ID1 = "@001A";
    private static final String INVALID_ID2 = "STAFF";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " !";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_PATIENT_ID = "PATIENT01";
    private static final String VALID_STAFF_ID = "STAFF001A";
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
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(Long.toString(
                Integer.MAX_VALUE + 1)));
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
    public void parsePhone_whitespace_returnsEmptyPhone() throws Exception {
        assertEquals(Phone.EMPTY_PHONE_DETAILS, ParserUtil.parsePhone(WHITESPACE));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
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
    public void parseAddress_whitespace_returnsEmptyAddress() throws Exception {
        assertEquals(Address.EMPTY_ADDRESS_DETAILS, ParserUtil.parseAddress(WHITESPACE));
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
    public void parseEmail_whitespace_returnsEmptyEmail() throws Exception {
        assertEquals(Email.EMPTY_EMAIL_DETAILS, ParserUtil.parseEmail(WHITESPACE));
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
        Tag expectedTag = Tag.issueTag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = Tag.issueTag(VALID_TAG_1);
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
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(Tag.issueTag(VALID_TAG_1), Tag.issueTag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parsePatientReferenceId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, PersonReferenceId.MESSAGE_CONSTRAINTS, () -> {
            ParserUtil.issuePatientReferenceId(INVALID_ID1);
        });

        assertThrows(ParseException.class, PersonReferenceId.MESSAGE_CONSTRAINTS, () -> {
            ParserUtil.issueStaffReferenceId(INVALID_ID1);
        });
    }

    @Test
    public void parsePersonReferenceId_validPatientIdWithoutWhitespace_returnsReferenceId() throws Exception {
        ReferenceId expectedId = PersonReferenceId.issuePatientReferenceId(VALID_PATIENT_ID);
        assertEquals(expectedId, ParserUtil.issuePatientReferenceId(VALID_PATIENT_ID));

        expectedId = PersonReferenceId.issueStaffReferenceId(VALID_STAFF_ID);
        assertEquals(expectedId, ParserUtil.issueStaffReferenceId(VALID_STAFF_ID));
    }

    @Test
    public void parsePatientReferenceId_validPatientIdWithWhitespace_beforeRegistering() throws Exception {

        final String patientIdWithWhitespace = WHITESPACE + VALID_PATIENT_ID + WHITESPACE;
        ReferenceId expectedId = PersonReferenceId.issuePatientReferenceId(VALID_PATIENT_ID);

        // True: equal
        assertEquals(expectedId, ParserUtil.issuePatientReferenceId(patientIdWithWhitespace));

        // True: the string form of the id are the same
        assertEquals(expectedId, ParserUtil.issueStaffReferenceId(patientIdWithWhitespace));

        final String staffIdWithWhitespace = WHITESPACE + VALID_STAFF_ID + WHITESPACE;
        expectedId = PersonReferenceId.issueStaffReferenceId(VALID_STAFF_ID);

        // True: equal
        assertEquals(expectedId, ParserUtil.issueStaffReferenceId(staffIdWithWhitespace));

        // True: the string form of the id are the same
        assertEquals(expectedId, ParserUtil.issuePatientReferenceId(staffIdWithWhitespace));
    }

    @Test
    public void parsePatientReferenceId_validPatientIdWithWhitespace_afterRegistering() throws Exception {

        final String patientIdWithWhitespace = WHITESPACE + VALID_PATIENT_ID + WHITESPACE;
        ReferenceId expectedId = PersonReferenceId.issuePatientReferenceId(VALID_PATIENT_ID);

        expectedId.registerId();

        // True: equal
        assertEquals(expectedId, ParserUtil.issuePatientReferenceId(patientIdWithWhitespace));

        // Throws error as id has been registered as a patient
        assertThrows(ReferenceIdCannotChangeClassificationException.class, () -> {
            ParserUtil.issueStaffReferenceId(patientIdWithWhitespace);
        });

        final String staffIdWithWhitespace = WHITESPACE + VALID_STAFF_ID + WHITESPACE;
        expectedId = PersonReferenceId.issueStaffReferenceId(VALID_STAFF_ID);

        expectedId.registerId();

        // True: equal
        assertEquals(expectedId, ParserUtil.issueStaffReferenceId(staffIdWithWhitespace));

        // Throws error as id has been registered as a staff
        assertThrows(ReferenceIdCannotChangeClassificationException.class, () -> {
            ParserUtil.issuePatientReferenceId(staffIdWithWhitespace);
        });

    }
}

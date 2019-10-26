package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;

public class AlfredParserUtilTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "xxx1234";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "98123456";
    private static final String VALID_EMAIL = "rachel@example.com";

    private static final String VALID_LOCATION_STRING = "20";
    private static final String INVALID_LOCATION_STRING = "Table 20";
    private static final int VALID_LOCATION_INT = 20;

    private static final String VALID_SUBJECT_1 = "Social";
    private static final String VALID_SUBJECT_2 = "Environmental";
    private static final String INVALID_SUBJECT = "Cat";

    private static final String SAMPLE_VALID_COMMAND1 = "participant P-1";
    private static final String SAMPLE_VALID_COMMAND1_SPECIFIER = "participant";
    private static final String SAMPLE_VALID_COMMAND1_ARGUMENT = " P-1";
    private static final String SAMPLE_INVALID_COMMAND = " ";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        // Index is just a number without a prefix.
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseIndex("10", PrefixType.M));

        // Prefix without index.
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseIndex("M", PrefixType.M));

        // Empty Index
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseIndex(" ", PrefixType.M));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        // The index has a valid format and prefix, but is out of the range of integer.
        assertThrows(ParseException.class, Messages.MESSAGE_INVALID_INDEX, ()
            -> AlfredParserUtil.parseIndex("M" + Long.toString(Integer.MAX_VALUE + 1), PrefixType.M));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        Id indexToTest = new Id(PrefixType.M, 1);
        assertEquals(indexToTest, AlfredParserUtil.parseIndex("M-1", PrefixType.M));

        // Leading and trailing whitespaces
        assertEquals(indexToTest, AlfredParserUtil.parseIndex("  M-1  ", PrefixType.M));
    }

    @Test
    void parseIndex_correctIndexFormat_noExceptionThrown() throws ParseException {
        Id id1 = new Id(PrefixType.P, 2);
        Id id2 = new Id(PrefixType.M, 9);
        Id id3 = new Id(PrefixType.T, 24);
        assertEquals(id1, AlfredParserUtil.parseIndex("P-2", PrefixType.P));
        assertEquals(id2, AlfredParserUtil.parseIndex("M-9", PrefixType.M));
        assertEquals(id3, AlfredParserUtil.parseIndex("T-24", PrefixType.T));
    }

    @Test
    void parseIndex_incorrectIndexFormat_parseExceptionThrown() {
        assertThrows(ParseException.class, Messages.MESSAGE_INVALID_INDEX, ()
            -> AlfredParserUtil.parseIndex("P2", PrefixType.P));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AlfredParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, AlfredParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, AlfredParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AlfredParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> AlfredParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, AlfredParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, AlfredParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AlfredParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, AlfredParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, AlfredParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AlfredParserUtil.parseLocation((String) null));
    }

    @Test
    void parseLocation_validLocationWithoutWhiteSpace_returnsLocation() throws ParseException {
        Location expectedLocation = new Location(VALID_LOCATION_INT);
        assertEquals(expectedLocation, AlfredParserUtil.parseLocation(VALID_LOCATION_STRING));
    }

    @Test
    void parseLocation_validLocationWithWhiteSpace_returnsLocation() throws ParseException {
        Location expectedLocation = new Location(VALID_LOCATION_INT);
        String locationWithSpace = WHITESPACE + VALID_LOCATION_STRING + WHITESPACE;
        assertEquals(expectedLocation, AlfredParserUtil.parseLocation(locationWithSpace));
    }

    @Test
    void parseLocation_invalidLocation_throwsParseException() {
        // Input string does not represent an integer - word table included.
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseLocation(INVALID_LOCATION_STRING));

        // Table number above valid range
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseLocation("1001"));

        // Table number below valid range
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseLocation("-1"));
    }

    @Test
    public void parseSubject_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AlfredParserUtil.parseSubject((String) null));
    }

    @Test
    void parseSubjectName_validSubjectName_subjectEnumReturned() throws ParseException {
        assertEquals(SubjectName.SOCIAL, AlfredParserUtil.parseSubject(VALID_SUBJECT_1));
        assertEquals(SubjectName.ENVIRONMENTAL, AlfredParserUtil.parseSubject(VALID_SUBJECT_2));
    }

    @Test
    void parseSubjectName_validSubjectNameWithWhiteSpace_subjectEnumReturned() throws ParseException {
        assertEquals(SubjectName.SOCIAL, AlfredParserUtil.parseSubject(WHITESPACE + VALID_SUBJECT_1 + WHITESPACE));
        assertEquals(SubjectName.ENVIRONMENTAL,
                AlfredParserUtil.parseSubject(WHITESPACE + VALID_SUBJECT_2 + WHITESPACE));
    }

    @Test
    void parseSubject_invalidSubject_throwsParseException() {
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseSubject(INVALID_SUBJECT));
    }

    @Test
    void getSpecifierFromCommand_validCommand_specifierReturned() throws ParseException {
        assertEquals(SAMPLE_VALID_COMMAND1_SPECIFIER, AlfredParserUtil.getSpecifierFromCommand(SAMPLE_VALID_COMMAND1));
    }

    @Test
    void getSpecifierFromCommand_invalidCommand_parseExceptionThrown() throws ParseException {
        assertThrows(ParseException.class, () -> AlfredParserUtil.getSpecifierFromCommand(SAMPLE_INVALID_COMMAND));
    }

    @Test
    void getArgumentsFromCommand_validCommand_argumentsReturned() throws ParseException {
        assertEquals(SAMPLE_VALID_COMMAND1_ARGUMENT, AlfredParserUtil.getArgumentsFromCommand(SAMPLE_VALID_COMMAND1));
    }

    @Test
    void getArgumentFromCommand_invalidCommand_parseExceptionThrown() throws ParseException {
        assertThrows(ParseException.class, () -> AlfredParserUtil.getArgumentsFromCommand(SAMPLE_INVALID_COMMAND));
    }

}

package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
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

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> AlfredParserUtil.parseIndex("10", PrefixType.M));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, Messages.MESSAGE_INVALID_INDEX, ()
            -> AlfredParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1), PrefixType.M));
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
    void parseIndex_incorrectIndexFormat_parseExceptionThrown() throws ParseException {
        Id id = new Id(PrefixType.P, 2);
        try {
            assertEquals(id, AlfredParserUtil.parseIndex("P2", PrefixType.P));
        } catch (ParseException pe) {
            assertEquals(Messages.MESSAGE_INVALID_INDEX, pe.getMessage());
        }
    }

    @Test
    void parseSubjectName_validSubjectName_appropriateEnumReturned() throws ParseException {
        assertEquals(SubjectName.SOCIAL, AlfredParserUtil.parseSubject("Social"));
        assertEquals(SubjectName.ENVIRONMENTAL, AlfredParserUtil.parseSubject("Environmental"));
        assertEquals(SubjectName.HEALTH, AlfredParserUtil.parseSubject("Health"));
        assertEquals(SubjectName.EDUCATION, AlfredParserUtil.parseSubject("Education"));
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
}

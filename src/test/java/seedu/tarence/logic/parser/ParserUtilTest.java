package seedu.tarence.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_INDEX_NON_POSITIVE;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.tutorial.Week;

class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_EMAIL = "rachel@example.com";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX_NON_POSITIVE, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_IN_LIST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_IN_LIST, ParserUtil.parseIndex("  1  "));
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
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
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
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    void parseDuration_validValue_returnsDuration() throws ParseException {
        String duration = "120";
        assertEquals(Duration.ofMinutes(Integer.parseInt(duration)), ParserUtil.parseDuration(duration));
    }

    @Test
    void parseDuration_invalidValue_throwsParseException() throws ParseException {
        String duration = "notANumber";
        assertThrows(ParseException.class, () -> ParserUtil.parseDuration(duration));
    }

    @Test
    void parseWeeks_validListOfWeeks_returnsWeeks() throws ParseException {
        String listOfWeeks = "1,2,4,5,10";
        Set<Week> expectedWeeks = Stream.of(1, 2, 4, 5, 10)
                .map(Week::new)
                .collect(Collectors.toSet());
        assertEquals(expectedWeeks, ParserUtil.parseWeeks(listOfWeeks));
    }

    @Test
    void parseWeeks_invalidListOfWeeksLessThanOne_returnsWeeks() {
        String listOfWeeks = "0,1,2,4,5,10";
        assertThrows(ParseException.class, () -> ParserUtil.parseWeeks(listOfWeeks));
    }

    @Test
    void parseWeeks_invalidListOfWeeksGreaterThanThirteen_returnsWeeks() {
        String listOfWeeks = "1,2,4,5,10,14";
        assertThrows(ParseException.class, () -> ParserUtil.parseWeeks(listOfWeeks));
    }

    @Test
    void parseWeeks_validRangeOfWeeks_returnsWeeks() throws ParseException {
        String rangeOfWeeks = "3-7";
        Set<Week> expectedWeeks = Stream.of(3, 4, 5, 6, 7)
                .map(Week::new)
                .collect(Collectors.toSet());
        assertEquals(expectedWeeks, ParserUtil.parseWeeks(rangeOfWeeks));
    }

    @Test
    void parseWeeks_invalidRangeOfWeeksLessThanOne_throwsParseException() {
        String listOfWeeks = "0-5";
        assertThrows(ParseException.class, () -> ParserUtil.parseWeeks(listOfWeeks));
    }

    @Test
    void parseWeeks_invalidRangeOfWeeksGreaterThanThirteen_throwsParseException() {
        String listOfWeeks = "10-14";
        assertThrows(ParseException.class, () -> ParserUtil.parseWeeks(listOfWeeks));
    }

    @Test
    void parseWeeks_validWeeksDescription_returnsWeeks() throws ParseException {
        String weeksDescription = "even";
        Set<Week> expectedWeeks = Stream.of(4, 6, 8, 10, 12)
                .map(Week::new)
                .collect(Collectors.toSet());
        assertEquals(expectedWeeks, ParserUtil.parseWeeks(weeksDescription));
    }

    @Test
    void parseWeeks_invalidWeeksDescription_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeeks("some invalid description"));
    }

    @Test
    void parseDayOfWeek_validInput_returnsDayOfWeek() throws ParseException {
        String shortFormLowerCase = "mon";
        String fullFormLowerCase = "monday";
        String shortFormUpperCase = "Mon";
        String fullFormUpperCase = "Monday";

        DayOfWeek expectedDayOfWeek = DayOfWeek.valueOf("MONDAY");

        assertEquals(expectedDayOfWeek, ParserUtil.parseDayOfWeek(shortFormLowerCase));
        assertEquals(expectedDayOfWeek, ParserUtil.parseDayOfWeek(fullFormLowerCase));
        assertEquals(expectedDayOfWeek, ParserUtil.parseDayOfWeek(shortFormUpperCase));
        assertEquals(expectedDayOfWeek, ParserUtil.parseDayOfWeek(fullFormUpperCase));
    }

    @Test
    void parseDayOfWeek_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDayOfWeek("Noneday"));
    }

    @Test
    void parseLocalTime_validInput_returnsLocalTime() throws ParseException {
        assertEquals(LocalTime.parse("23:59:00"), ParserUtil.parseLocalTime("2359"));
    }

    @Test
    void parseLocalTime_invalidInputWrongLength_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalTime("12345"));
    }

    @Test
    void parseLocalTime_invalidInputNonNumerical_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalTime("abcde"));
    }

    @Test
    void parseLocalTime_invalidInputOutOfRange_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalTime("2400"));
    }

    @Test
    void parseMatricNum_validInput_returnsMatricNum() throws ParseException {
        String matricNum = "A1234567A";
        assertEquals(new MatricNum(matricNum), ParserUtil.parseMatricNum(matricNum));
    }

    @Test
    void parseMatricNum_invalidInputWrongLength_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMatricNum("A12345678A"));
    }

    @Test
    void parseMatricNum_invalidInputWrongFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMatricNum("A123456ZA"));
    }

    @Test
    void parseNusnetId_validInput_returnsNusnetId() throws ParseException {
        String nusnetId = "E0123456";
        assertEquals(new NusnetId(nusnetId), ParserUtil.parseNusnetId(nusnetId));
    }

    @Test
    void parseNusnetId_invalidInputWrongLength_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNusnetId("E01234567"));
    }

    @Test
    void parseNusnetId_invalidInputWrongFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNusnetId("E012345A"));
    }

    @Test
    void parseModCode_validInputTwoLettersNoSuffix_returnsModCode() throws ParseException {
        String modCode = "AB1234";
        assertEquals(new ModCode(modCode), ParserUtil.parseModCode(modCode));
    }

    @Test
    void parseModCode_validInputThreeLettersNoSuffix_returnsModCode() throws ParseException {
        String modCode = "ABC1234";
        assertEquals(new ModCode(modCode), ParserUtil.parseModCode(modCode));
    }

    @Test
    void parseModCode_validInputTwoLettersWithSuffix_returnsModCode() throws ParseException {
        String modCode = "AB1234Z";
        assertEquals(new ModCode(modCode), ParserUtil.parseModCode(modCode));
    }

    @Test
    void parseModCode_validInputThreeLettersWithSuffix_returnsModCode() throws ParseException {
        String modCode = "ABC1234Z";
        assertEquals(new ModCode(modCode), ParserUtil.parseModCode(modCode));
    }
}

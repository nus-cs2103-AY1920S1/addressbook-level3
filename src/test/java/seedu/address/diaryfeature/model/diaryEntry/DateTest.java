package seedu.address.diaryfeature.model.diaryEntry;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DateParseException;

public class DateTest {

    @Test
    public void invalid_argument_for_date_throws_DateParseException() {
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate(""));//empty
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("  "));// spaces
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("iweiheir")); //letters
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("36424692649"));//numbers only
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("uwheiu8743h"));///chars
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("12122019 1200"));///no slashes
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("12/122019 1200"));/// not enough slashes
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("1/2/1/2/2/01/9 12/00/"));/// too many slashes
        assertThrows(DateParseException.class, () -> DateFormatter.convertToDate("1200 12/12/2019"));/// wrong format
    }




}

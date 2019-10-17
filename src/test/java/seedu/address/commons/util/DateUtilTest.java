package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests DateUtil.
 */
class DateUtilTest {

    @Test
    public void parseDate_validDate_correctResult() throws ParseException {
        Calendar c = Calendar.getInstance();
        c.set(2019, Calendar.DECEMBER, 25, 0, 0, 0);

        Date date = c.getTime();

        // Manually fix the date so Natty passes.
        // mm/dd/yyyy will not be supported.
        Date parsedDate2 = DateUtil.parseDate("25/12/2019 midnight");
        assertEquals(DateUtil.formatDate(date), DateUtil.formatDate(parsedDate2));

        c.set(2019, Calendar.OCTOBER, 12, 0, 0, 0);
        date = c.getTime();

        Date parsedDate3 = DateUtil.parseDate("12/10/2019 midnight");
        assertEquals(DateUtil.formatDate(date), DateUtil.formatDate(parsedDate3));

        assertThrows(ParseException.class, () -> DateUtil.parseDate("does not work"));
    }

    @Test
    public void parseDate_isValidDate_correctResult() {
        assertFalse(DateUtil.isValidDate("1"));
        assertFalse(DateUtil.isValidDate("2"));
        assertFalse(DateUtil.isValidDate("2/2/79"));
        assertTrue(DateUtil.isValidDate("2/2"));
    }
}

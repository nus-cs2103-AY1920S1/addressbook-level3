package seedu.moneygowhere.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.parser.exceptions.ParseException;

/**
 * Tests DateUtil.
 */
class DateUtilTest {

    @Test
    public void parseDate_validDate_correctResult() throws ParseException {
        LocalDate date = LocalDate.of(2019, Month.DECEMBER, 25);
        // mm/dd/yyyy will not be supported.
        assertEquals(date, DateUtil.parseDate("25/12/2019 midnight"));

        date = LocalDate.of(2019, Month.OCTOBER, 12);

        assertEquals(date, DateUtil.parseDate("12/10/2019 midnight"));
        assertEquals(date, DateUtil.parseDate("12-10-2019 midnight"));
        assertThrows(ParseException.class, () -> DateUtil.parseDate("does not work"));
    }

    @Test
    public void parseDate_isValidDate_correctResult() {
        assertFalse(DateUtil.isValidDate("1"));
        assertFalse(DateUtil.isValidDate("2"));
        assertFalse(DateUtil.isValidDate("2/2/79"));
        assertTrue(DateUtil.isValidDate("2/2"));
        assertTrue(DateUtil.isValidDate("2-2"));
    }

    @Test
    public void formatDate_correctResult() {
        assertEquals("25/12/2019", DateUtil.formatDate(LocalDate.of(2019, Month.DECEMBER, 25)));
    }

    @Test
    public void prettyFormatDate_correctResult() {
        assertEquals("Wed 25/12/2019", DateUtil.prettyFormatDate("25/12/2019"));
        assertEquals("", DateUtil.prettyFormatDate(" "));
    }

    @Test
    public void parseDates_validDates() {
        LocalDate date1 = LocalDate.of(2019, Month.OCTOBER, 12);
        LocalDate date2 = LocalDate.of(2019, Month.OCTOBER, 13);

        List<LocalDate> dates = DateUtil.parseDates("12/10 - 13/10");
        assertNotNull(dates);
        assertEquals(2, dates.size());
        assertEquals(date1, dates.get(0));
        assertEquals(date2, dates.get(1));
    }

    @Test
    public void parseDates_invalidDates() {
        List<LocalDate> dates = DateUtil.parseDates("no");
        assertNull(dates);
    }
}

package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests DateUtil.
 */
class DateUtilTest {

    @Test
    public void parseDate_validDate() throws ParseException {
        Calendar c = Calendar.getInstance();
        c.set(2019, Calendar.DECEMBER, 25, 0, 0, 0);
        Date date = c.getTime();

        // Known bug: Natty does not parse 25/12/2019
        Date parsedDate = DateUtil.parseDate("12/25/2019 midnight");

        assertEquals(DateUtil.formatDate(date), DateUtil.formatDate(parsedDate));
        assertThrows(ParseException.class, () -> DateUtil.parseDate("does not work"));
    }
}

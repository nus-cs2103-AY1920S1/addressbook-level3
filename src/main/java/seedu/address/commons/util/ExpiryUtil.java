package seedu.address.commons.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * A class for calculating time to expiry dates.
 */
public class ExpiryUtil {

    private static DateTimeFormatter dateTimeFormat = new DateTimeFormatterBuilder()
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .appendPattern("MM/yy")
            .toFormatter();

    /**
     * Calculates the number of months until the expiry given.
     * @param expiry date in format MM/YY.
     * @return number of months until expiry.
     */
    public static int getMonthToExp(String expiry) {
        LocalDate date = LocalDate.parse(expiry, dateTimeFormat);
        Period period = LocalDate.now().until(date);
        return period.getMonths() + period.getYears() * 12 + (period.getDays() > 0 ? 1 : 0);
    }
}

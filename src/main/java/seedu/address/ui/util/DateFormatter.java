package seedu.address.ui.util;

import java.time.LocalDate;

/**
 * A class to format dates.
 */
public class DateFormatter {
    public static String formatToString(LocalDate localDate) {
        return localDate.getDayOfMonth() + "/" + localDate.getMonthValue();
    }
}

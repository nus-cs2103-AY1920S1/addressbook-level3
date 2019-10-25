package seedu.address.ui.util;

import java.time.LocalDate;

/**
 * A class to format dates.
 */
public class DateFormatter {
    public static String formatToString(LocalDate localDate) {
        String date = localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth() : "" + localDate.getDayOfMonth();
        return date + "/" + localDate.getMonthValue();
    }
}

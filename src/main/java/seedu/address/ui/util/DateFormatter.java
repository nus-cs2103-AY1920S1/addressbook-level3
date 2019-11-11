package seedu.address.ui.util;

import java.time.LocalDate;

/**
 * A class to format dates.
 */
public class DateFormatter {
    /**
     * Method to format date to string. Example: 18 OCTOBER 2019 will become 18/09.
     * @param date input date to be formatted
     * @return A string that represents the date.
     */
    public static String formatToString(LocalDate date) {
        String month = date.getMonthValue() < 10 ? "0" + date.getMonthValue() : "" + date.getMonthValue();
        String day = date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : "" + date.getDayOfMonth();
        return day + "/" + month;
    }
}

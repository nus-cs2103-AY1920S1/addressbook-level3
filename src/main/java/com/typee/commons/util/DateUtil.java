package com.typee.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

/**
 * A container for date specific utility functions
 */
public class DateUtil {

    /**
     * Returns a formatted date string based on the specified date.
     * @param date The specified date.
     * @return A formatted date string.
     */
    public static String getFormattedDateString(LocalDate date) {
        requireNonNull(date);
        String dayString = String.format("%02d", date.getDayOfMonth());
        String monthString = String.format("%02d", date.getMonthValue());
        String yearString = String.format("%04d", date.getYear());
        return String.format("%s/%s/%s", dayString, monthString, yearString);
    }

}

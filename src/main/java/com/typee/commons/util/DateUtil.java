package com.typee.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import com.typee.model.engagement.TimeSlot;

/**
 * A container for date specific utility functions
 */
public class DateUtil {

    private static final int MINIMUM_YEAR = 1;
    private static final int MAXIMUM_YEAR = 9999;

    /**
     * Returns a formatted date string based on the specified date.
     *
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

    /**
     * Returns true if the specified year is valid.
     *
     * @param year The specified year.
     * @return True if the specified year is valid.
     */
    public static boolean isValidYear(int year) {
        return year >= MINIMUM_YEAR && year <= MAXIMUM_YEAR;
    }

    /**
     * Returns true if the specified date is within the specified time slot.
     *
     * @param date The specified date.
     * @param timeSlot The specified time slot.
     * @return True if the specified date is within the specified time slot.
     */
    public static boolean isWithinTimeSlot(LocalDate date, TimeSlot timeSlot) {
        LocalDateTime startDateTime = timeSlot.getStartTime();
        LocalDateTime endDateTime = timeSlot.getEndTime();
        LocalDate startDate = LocalDate.of(startDateTime.getYear(), startDateTime.getMonthValue(),
                startDateTime.getDayOfMonth());
        LocalDate endDate = LocalDate.of(endDateTime.getYear(), endDateTime.getMonthValue(),
                endDateTime.getDayOfMonth());
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    /**
     * Returns true if the specified {@code YearMonth} and {@code LocalDate} have the same month and year.
     *
     * @param yearMonth The specified {@code YearMonth}.
     * @param date The specified {@code LocalDate}.
     * @return True if the specified {@code YearMonth} and {@code LocalDate} have the same month and year.
     */
    public static boolean hasSameMonthAndYear(YearMonth yearMonth, LocalDate date) {
        return date.getYear() == yearMonth.getYear() && date.getMonthValue() == yearMonth.getMonthValue();
    }

}

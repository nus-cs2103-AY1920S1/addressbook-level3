package seedu.address.calendar.model;

import java.util.stream.IntStream;

/**
 * Simplified and static calendar
 */
public class CalendarSimplified extends Calendar {
    int days[] = {1, 2, 3, 4, 5, 6, 7};

    public IntStream getDays() {
        return IntStream.of(days);
    }

}
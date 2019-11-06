package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A utility class containing a list of Deadline objects to be used in tests.
 */
public class TypicalDeadlines {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public static final LocalDate NOV_DEADLINE = LocalDate.parse("01-Nov-2019", DATE_TIME_FORMATTER);
    public static final LocalDate DEC_DEADLINE = LocalDate.parse("02-Dec-2019", DATE_TIME_FORMATTER);
}

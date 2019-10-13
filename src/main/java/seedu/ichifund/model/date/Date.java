package seedu.ichifund.model.date;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.logging.Logger;

import seedu.ichifund.commons.core.LogsCenter;

/**
 * Represents a Date in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Date implements Comparable<Date> {
    private static final Logger logger = LogsCenter.getLogger(Date.class);

    private java.util.Date date;
    private Day day;
    private Month month;
    private Year year;

    /**
     * Constructs a {@code Date}.
     *
     * @param day A valid day.
     * @param month A valid month.
     * @param year A valid year.
     */
    public Date(Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("d/M/yyyy");

        try {
            this.date = dateFormatter.parse(day + "/" + month + "/" + year);
        } catch (ParseException e) {
            logger.warning("java.text.ParseException encountered.");
        }

        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    @Override
    public int compareTo(Date other) {
        return this.date.compareTo(other.date);
    }
}

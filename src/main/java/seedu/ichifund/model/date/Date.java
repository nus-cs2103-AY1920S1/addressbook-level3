package seedu.ichifund.model.date;


import seedu.ichifund.commons.core.LogsCenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Transaction in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Date implements Comparable<Date>{
    private java.util.Date date;
    private Day day;
    private Month month;
    private Year year;

    private static final Logger logger = LogsCenter.getLogger(Date.class);

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

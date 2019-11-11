package seedu.ichifund.testutil;

import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;

/**
 * A utility class to help with building Date objects.
 */
public class DateBuilder {
    public static final String DEFAULT_DAY = "10";
    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2019";

    private Day day;
    private Month month;
    private Year year;

    public DateBuilder() {
        day = new Day(DEFAULT_DAY);
        month = new Month(DEFAULT_MONTH);
        year = new Year(DEFAULT_YEAR);
    }

    /**
     * Initializes the DateBuilder with the data of {@code dateToCopy}.
     */
    public DateBuilder(Date dateToCopy) {
        day = dateToCopy.getDay();
        month = dateToCopy.getMonth();
        year = dateToCopy.getYear();
    }

    /**
     * Sets the {@code Day} of the {@code Date} that we are building.
     */
    public DateBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Month} of the {@code Date} that we are building.
     */
    public DateBuilder withMonth(String month) {
        this.month = new Month(month);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Date} that we are building.
     */
    public DateBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }


    public Date build() {
        return new Date(day, month, year);
    }

}

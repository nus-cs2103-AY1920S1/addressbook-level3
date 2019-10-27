package seedu.address.testutil;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;

/**
 * A utility class to help with building AthletickDate objects.
 */
public class AthletickDateBuilder {

    private static final int DEFAULT_DAY = 1;
    private static final int DEFAULT_MONTH = 1;
    private static final int DEFAULT_YEAR = 2019;
    private static final int DEFAULT_TYPE = 1;
    private static final String DEFAULT_MTH = "January";

    private int day;
    private int month;
    private int year;
    private int type;
    private String mth;

    public AthletickDateBuilder() {
        day = DEFAULT_DAY;
        month = DEFAULT_MONTH;
        year = DEFAULT_YEAR;
        type = DEFAULT_TYPE;
        mth = DEFAULT_MTH;
    }

    /**
     * Sets the {@code day} of the {@code AthletickDateBuilder} that we are building.
     */
    public AthletickDateBuilder withDay(int day) {
        this.day = day;
        return this;
    }

    /**
     * Sets the {@code month} of the {@code AthletickDateBuilder} that we are building.
     */
    public AthletickDateBuilder withMonth(int month) {
        this.month = month;
        return this;
    }

    /**
     * Sets the {@code year} of the {@code AthletickDateBuilder} that we are building.
     */
    public AthletickDateBuilder withYear(int year) {
        this.year = year;
        return this;
    }

    /**
     * Sets the {@code type} of the {@code AthletickDateBuilder} that we are building.
     */
    public AthletickDateBuilder withType(int type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the {@code mth} of the {@code AthletickDateBuilder} that we are building.
     */
    public AthletickDateBuilder withMth(String mth) {
        this.mth = mth;
        return this;
    }

    public AthletickDate build() throws ParseException {
        return new AthletickDate(day, month, year, type, mth);
    }
}

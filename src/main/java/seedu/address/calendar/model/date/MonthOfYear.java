package seedu.address.calendar.model.date;

/**
 * Represents month of the year, i.e. January, February, etc. For easy mathematical manipulations,
 * each month of year has an associated numerical value.
 */
public enum MonthOfYear {
    /**
     * Represents January, with associated numerical value 1.
     */
    JANUARY(1, 31),
    /**
     * Represents February, with associated numerical value 2.
     */
    FEBRUARY(2, 28),
    /**
     * Represents March, with associated numerical value 3.
     */
    MARCH(3, 31),
    /**
     * Represents April, with associated numerical value 4.
     */
    APRIL(4, 30),
    /**
     * Represents May, with associated numerical value 5.
     */
    MAY(5, 31),
    /**
     * Represents June, with associated numerical value 6.
     */
    JUNE(6, 30),
    /**
     * Represents July, with associated numerical value 7.
     */
    JULY(7, 31),
    /**
     * Represents August, with associated numerical value 8.
     */
    AUGUST(8, 31),
    /**
     * Represents September, with associated numerical value 9.
     */
    SEPTEMBER(9, 30),
    /**
     * Represents October, with associated numerical value 10.
     */
    OCTOBER(10, 31),
    /**
     * Represents November, with associated numerical value 11.
     */
    NOVEMBER(11, 30),
    /**
     * Represents December, with associated numerical value 12.
     */
    DECEMBER(12, 31);

    private static int DAYS_IN_FEB_LEAP = 29;
    private int numericalVal;
    private int numDaysInMonth;

    /**
     * Constructs a {@code MonthOfYear}.
     *
     * @param numericalVal Numerical representation of {@code this}
     * @param numDaysInMonth Default number of days of {@code this}
     */
    MonthOfYear(int numericalVal, int numDaysInMonth) {
        this.numericalVal = numericalVal;
        this.numDaysInMonth = numDaysInMonth;
    }

    /**
     * Gets the associated numerical value of {@code this}.
     *
     * @return Associated numerical value of {@code this}
     */
    public int getNumericalVal() {
        return numericalVal;
    }

    /**
     * Gets the number of days in {@code this} when it is {@code year}.
     *
     * @param year Desired year
     * @return Number of days in {@code this} when it is {@code year}
     */
    public int getNumDaysInMonth(Year year) {
        if (numericalVal == 2) {
            // if it is February
            return year.isLeapYear() ? DAYS_IN_FEB_LEAP : numDaysInMonth;
        }
        return numDaysInMonth;
    }

    /**
     * Returns a capitalised string version of {@code this}.
     *
     * @return A capitalised string version of {@code this}
     */
    @Override
    public String toString() {
        switch(this) {
        case JANUARY:
            return "January";
        case FEBRUARY:
            return "February";
        case MARCH:
            return "March";
        case APRIL:
            return "April";
        case MAY:
            return "May";
        case JUNE:
            return "June";
        case JULY:
            return "July";
        case AUGUST:
            return "August";
        case SEPTEMBER:
            return "September";
        case OCTOBER:
            return "October";
        case NOVEMBER:
            return "November";
        case DECEMBER:
            return "December";
        }

        assert false : "Month of year should be valid";
        return ""; // this should not be returned
    }
}

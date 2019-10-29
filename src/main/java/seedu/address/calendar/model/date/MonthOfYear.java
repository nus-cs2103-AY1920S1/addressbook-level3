package seedu.address.calendar.model.date;

public enum MonthOfYear {
    JANUARY(1, 31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    private static int NUM_MONTHS_IN_YEAR = 12;
    private static int DAYS_IN_FEB_LEAP = 29;
    private int numericalVal;
    private int numDaysInMonth;

    MonthOfYear(int numericalVal, int numDaysInMonth) {
        this.numericalVal = numericalVal;
        this.numDaysInMonth = numDaysInMonth;
    }

    public int getNumericalVal() {
        return numericalVal;
    }

    public int getNumDaysInMonth(Year year) {
        if (numericalVal == 2) {
            // if it is February
            return isLeapYear(year.getNumericalValue()) ? DAYS_IN_FEB_LEAP : numDaysInMonth;
        }
        return numDaysInMonth;
    }

    public int getNumDaysInMonth(MonthOfYear monthOfYear, Year year) {
        int numericalVal = monthOfYear.getNumericalVal();
        int numDaysInMonth = monthOfYear.getNumDaysInMonth(year);
        if (numericalVal == 2) {
            // if it is February
            return isLeapYear(year.getNumericalValue()) ? DAYS_IN_FEB_LEAP : numDaysInMonth;
        }
        return numDaysInMonth;
    }

    // mathematical approach to determine whether it is a leap year
    private boolean isLeapYear(int year) {
        if ((year % 4 == 0) && (year % 100 != 0)) {
            return true;
        } else if (year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

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

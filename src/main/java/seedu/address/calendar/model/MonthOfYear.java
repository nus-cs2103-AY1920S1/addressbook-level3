package seedu.address.calendar.model;

import java.util.stream.Stream;

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

    int getNumericalVal() {
        return numericalVal;
    }

    int getNumDaysInMonth(int year) {
        if (numericalVal == 2) {
            // if it is February
            return isLeapYear(year) ? DAYS_IN_FEB_LEAP : numDaysInMonth;
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

    static int getNumMonthsInYear() {
        return NUM_MONTHS_IN_YEAR;
    }

    static MonthOfYear convertJavaMonth(int javaMonth) {
        return MonthOfYear.values()[javaMonth];
    }

    public static MonthOfYear convertNumToMonth(int monthNum) {
        return convertJavaMonth(monthNum);
    }

    public static MonthOfYear convertStrToMonth(String monthStr) {
        return Stream.of(MonthOfYear.values())
                .filter(month -> {
                    String monthLowerCase = month.toString().toLowerCase();
                    String monthStrLowerCase = monthStr.toLowerCase();
                    return monthLowerCase.contains(monthStrLowerCase);
                })
                .findFirst()
                .orElse(JANUARY); // todo: handle exception
    }
}

package seedu.address.calendar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Month {
    private static int FIRST_DAY_OF_MONTH = 1;
    MonthOfYear monthOfYear;
    List<Day> days = new ArrayList<>();
    int year;
    int daysInMonth;

    public Month(MonthOfYear monthOfYear, int year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.daysInMonth = monthOfYear.getNumDaysInMonth(year);
        generateDays();
    }

    void generateDays() {
        int firstDayOfWeekAsNum = findFirstDayOfWeekAsNum();
        IntStream.range(0, daysInMonth)
                .mapToObj(dayOfMonth -> {
                    int dayOfWeekAsNum = (firstDayOfWeekAsNum + dayOfMonth) % 7;
                    DayOfWeek dayOfWeek = DayOfWeek.of(dayOfWeekAsNum);
                    return Day.getOneBased(dayOfWeek, dayOfMonth);
                })
                .forEach(day -> days.add(day));
    }

    public Stream<Day> getDaysInMonth() {
        return days.stream();
    }

    public Day getFirstDayOfMonth() {
        return days.get(0);
    }

    public MonthOfYear getMonthOfYear() {
        return monthOfYear;
    }

    /**
     * Computes which day (of week) {@code} month starts on
     * @return day (of week) {@code this} month starts on
     */
    private int findFirstDayOfWeekAsNum() {
        int monthNumerical = monthOfYear.getNumericalVal();
        int zellerMonth = findZellerMonth(monthNumerical);
        int zellerYear = findZellerYear(zellerMonth);
        int zellerCentury = zellerYear / 100;
        int lastTwoDigitsOfYear = zellerYear % 100;

        // use Zeller's formula
        int day = ((FIRST_DAY_OF_MONTH + (13 * zellerMonth - 1) / 5 + lastTwoDigitsOfYear + (lastTwoDigitsOfYear / 4)
                + (zellerCentury / 4) - 2 * zellerCentury)) % 7;
        int positiveDay = day < 0 ? (day + 7) : day;

        return positiveDay;
    }

    /**
     * Computes the numerical value of {@code this} month such that it can be easily used with Zeller's rule.
     *
     * @param monthNumerical numerical representation of {@code this} month
     * @return numerical value of {@code this} month such that it can be easily used with Zeller's rule
     */
    private int findZellerMonth(int monthNumerical) {
        int shiftedMonth = ((monthNumerical - 2) + MonthOfYear.getNumMonthsInYear()) % MonthOfYear.getNumMonthsInYear();
        return shiftedMonth;
    }

    /**
     * Computes the year such that it can be easily used with Zeller's rule.
     *
     * @param zellerMonth {@code this} month such that it can be easily used with Zeller's rule
     * @return year such that it can be easily used with Zeller's rule.
     */
    private int findZellerYear(int zellerMonth) {
        return zellerMonth > 10 ? (year - 1) : year;
    }
}

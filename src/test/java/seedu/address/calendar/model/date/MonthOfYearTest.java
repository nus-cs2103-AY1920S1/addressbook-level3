package seedu.address.calendar.model.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MonthOfYearTest {
    @Test
    public void getNumericalVal() {
        // ensures that {@code JANUARY} is represented by the {@code int} 1
        assertEquals(MonthOfYear.JANUARY.getNumericalVal(), 1);
        // ensures that {@code FEBRUARY} is represented by the {@code int} 2
        assertEquals(MonthOfYear.FEBRUARY.getNumericalVal(), 2);
        // ensures that {@code MARCH} is represented by the {@code int} 3
        assertEquals(MonthOfYear.MARCH.getNumericalVal(), 3);
        // ensures that {@code APRIL} is represented by the {@code int} 4
        assertEquals(MonthOfYear.APRIL.getNumericalVal(), 4);
        // ensures that {@code MAY} is represented by the {@code int} 5
        assertEquals(MonthOfYear.MAY.getNumericalVal(), 5);
        // ensures that {@code JUNE} is represented by the {@code int} 6
        assertEquals(MonthOfYear.JUNE.getNumericalVal(), 6);
        // ensures that {@code JULY} is represented by the {@code int} 7
        assertEquals(MonthOfYear.JULY.getNumericalVal(), 7);
        // ensures that {@code AUGUST} is represented by the {@code int} 8
        assertEquals(MonthOfYear.AUGUST.getNumericalVal(), 8);
        // ensures that {@code SEPTEMBER} is represented by the {@code int} 9
        assertEquals(MonthOfYear.SEPTEMBER.getNumericalVal(), 9);
        // ensures that {@code OCTOBER} is represented by the {@code int} 10
        assertEquals(MonthOfYear.OCTOBER.getNumericalVal(), 10);
        // ensures that {@code NOVEMBER} is represented by the {@code int} 11
        assertEquals(MonthOfYear.NOVEMBER.getNumericalVal(), 11);
        // ensures that {@code DECEMBER} is represented by the {@code int} 12
        assertEquals(MonthOfYear.DECEMBER.getNumericalVal(), 12);

    }

    @Test
    public void getNumDaysInMonth() {
        MonthOfYear jan = MonthOfYear.JANUARY;
        MonthOfYear feb = MonthOfYear.FEBRUARY;
        MonthOfYear mar = MonthOfYear.MARCH;
        MonthOfYear apr = MonthOfYear.APRIL;
        MonthOfYear may = MonthOfYear.MAY;
        MonthOfYear jun = MonthOfYear.JUNE;
        MonthOfYear jul = MonthOfYear.JULY;
        MonthOfYear aug = MonthOfYear.AUGUST;
        MonthOfYear sep = MonthOfYear.SEPTEMBER;
        MonthOfYear oct = MonthOfYear.OCTOBER;
        MonthOfYear nov = MonthOfYear.NOVEMBER;
        MonthOfYear dec = MonthOfYear.DECEMBER;

        Year leapYear1 = new Year(1984);
        Year leapYear2 = new Year(2020);
        Year nonLeapYear1 = new Year(1983);
        Year nonLeapYear2 = new Year(1985);
        Year nonLeapYear3 = new Year(2019);

        /* The following tests checks whether there are any unexpected differences in output for months that aren't
           affected by leap years.
         */
        assertEquals(31, jan.getNumDaysInMonth(leapYear1));
        assertEquals(31, jan.getNumDaysInMonth(nonLeapYear1));
        assertEquals(31, mar.getNumDaysInMonth(leapYear1));
        assertEquals(31, mar.getNumDaysInMonth(nonLeapYear1));
        assertEquals(30, apr.getNumDaysInMonth(leapYear1));
        assertEquals(30, apr.getNumDaysInMonth(nonLeapYear1));
        assertEquals(31, may.getNumDaysInMonth(leapYear1));
        assertEquals(31, may.getNumDaysInMonth(nonLeapYear1));
        assertEquals(30, jun.getNumDaysInMonth(leapYear1));
        assertEquals(30, jun.getNumDaysInMonth(nonLeapYear1));
        assertEquals(31, jul.getNumDaysInMonth(leapYear1));
        assertEquals(31, jul.getNumDaysInMonth(nonLeapYear1));
        assertEquals(31, aug.getNumDaysInMonth(leapYear1));
        assertEquals(31, aug.getNumDaysInMonth(nonLeapYear1));
        assertEquals(30, sep.getNumDaysInMonth(leapYear1));
        assertEquals(30, sep.getNumDaysInMonth(nonLeapYear1));
        assertEquals(31, oct.getNumDaysInMonth(leapYear1));
        assertEquals(31, oct.getNumDaysInMonth(nonLeapYear1));
        assertEquals(30, nov.getNumDaysInMonth(leapYear1));
        assertEquals(30, nov.getNumDaysInMonth(nonLeapYear1));
        assertEquals(31, dec.getNumDaysInMonth(leapYear1));
        assertEquals(31, dec.getNumDaysInMonth(nonLeapYear1));

        /* The following tests checks that the no. of days in February are calculated correctly */
        assertEquals(29, feb.getNumDaysInMonth(leapYear2));
        assertEquals(29, feb.getNumDaysInMonth(leapYear1));
        assertEquals(28, feb.getNumDaysInMonth(nonLeapYear1));
        assertEquals(28, feb.getNumDaysInMonth(nonLeapYear2));
        assertEquals(28, feb.getNumDaysInMonth(nonLeapYear3));
    }


}

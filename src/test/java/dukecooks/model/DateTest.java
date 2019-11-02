package dukecooks.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    void isValidDate() {
        // valid day
        assertTrue(Date.isValidDay(2, 2, 2018));
        assertTrue(Date.isValidDay(31, 12, 2019));

        // valid leap day
        assertTrue(Date.isValidDay(29, 2, 2016));
        assertTrue(Date.isValidDay(29, 2, 2020));

        //invalid day
        assertFalse(Date.isValidDay(-7, 5, 2018));
        assertFalse(Date.isValidDay(30, 2, 2020));
        assertFalse(Date.isValidDay(32, 4, 2018));
        assertFalse(Date.isValidDay(31, 9, 2000));
        assertFalse(Date.isValidDay(30, 2, 1997));
        assertFalse(Date.isValidDay(123, 12, 2000));

        // invalid leap day
        assertFalse(Date.isValidDay(29, 2, 1995));
        assertFalse(Date.isValidDay(29, 2, 2019));
    }

    @Test
    void generateDate() {
        Date toCompare = new Date(2, 2, 2020);
        assertTrue(toCompare.equals(Date.generateDate("02/02/2020")));
        assertTrue(toCompare.equals(Date.generateDate("2/2/2020")));
        assertFalse(toCompare.equals(Date.generateDate("02/02/2002")));
    }

    @Test
    void isValidDateFormat() {
        // valid date format
        assertTrue(Date.isValidDateFormat("02/02/2020"));
        assertTrue(Date.isValidDateFormat("2/2/2020"));

        // invalid date format
        assertFalse(Date.isValidDateFormat("02022020"));
        assertFalse(Date.isValidDateFormat("02-02-2020"));
    }

    @Test
    void isValidDay() {

        // valid date
        assertTrue(Date.isValidDate("12/12/2019"));

        // invalid date
        assertFalse(Date.isValidDate(""));

        // invalid day
        assertFalse(Date.isValidDate("123/01/2019"));

        // invalid month
        assertFalse(Date.isValidDate("12/029/2019"));

        // invalid year
        assertFalse(Date.isValidDate("12/12/222222"));
        assertFalse(Date.isValidDate("1212/2222"));
    }

    @Test
    void isValidFebruaryDay() {
        // valid leap day
        assertTrue(Date.isValidFebruaryDay(29, 2020));

        // invalid leap day
        assertFalse(Date.isValidFebruaryDay(29, 2019));
    }

    @Test
    void isValidMonth() {
        // valid month
        assertTrue(Date.isValidMonth(1));
        assertTrue(Date.isValidMonth(12));

        // invalid month
        assertFalse(Date.isValidMonth(0));
        assertFalse(Date.isValidMonth(-1));
        assertFalse(Date.isValidMonth(13));
        assertFalse(Date.isValidMonth(123));
    }

    @Test
    void isValidYear() {
        // valid year
        assertTrue(Date.isValidYear(2019));
        assertTrue(Date.isValidYear(1950));
        assertTrue(Date.isValidYear(3000));

        // invalid year
        assertFalse(Date.isValidYear(123));
        assertFalse(Date.isValidYear(-1000));
    }

    @Test
    void testEquals() {
        Date date1 = new Date(1, 1, 2000);
        Date date2 = new Date(1, 1, 2000);
        Date date3 = new Date(2, 2, 4000);

        // same object
        assertTrue(date1.equals(date1));

        // different object, same fields
        assertTrue(date1.equals(date2));

        // different objects and fields
        assertFalse(date2.equals(null));
        assertFalse(date3.equals(date2));
    }

    @Test
    void testToString() {
        Date date1 = new Date(1, 1, 2019);
        assertTrue(("01/01/2019".equals(date1.toString())));
    }
}

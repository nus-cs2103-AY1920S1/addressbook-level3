package seedu.address.calendar.model.date;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // null day of week
        assertThrows(NullPointerException.class, () -> new Date(null, MonthOfYear.DECEMBER, new Year(2019)));
        // null month of year
        assertThrows(NullPointerException.class, () -> new Date(new Day(DayOfWeek.WED, 6,
                MonthOfYear.NOVEMBER, new Year(2019)), null, new Year(2019)));
        // null year
        assertThrows(NullPointerException.class, () -> new Date(new Day(DayOfWeek.WED, 6,
                MonthOfYear.NOVEMBER, new Year(2019)), MonthOfYear.NOVEMBER, null));
    }

    @Test
    public void getInstanceFromString_invalidString_throwsIllegalValueException() {
        // missing DDD
        String missingDDD = ", 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingDDD));
        // only DD
        String onlyDD = "Sa, 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(onlyDD));
        // with DDDD
        String DDDD = "Satu, 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(DDDD));

        // missing comma
        String missingComma = "Sat 31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingComma));
        // missing spacing
        String missingSpacing = "Sat,31 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingSpacing));
        // missing dd
        String missingDd = "Sat, October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingDd));
        // ddd
        String Ddd = "Tue, 001 October 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(Ddd));

        // missing MMM
        String missingMMM = "Sat, 31 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingMMM));
        // only MM
        String onlyMM = "Sat, 31 Oc 2019";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(onlyMM));

        // missing yyyy
        String missingYyyy = "Sat, 31 October ";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(missingYyyy));
        // only yyy
        String onlyYyy = "Sat, 31 October 201";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(onlyYyy));
        // extra y
        String extraY = "Sat, 31 October 20190";
        assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(extraY));

        // illegal day of week

        // invalid month string

        // invalid year

        // specified date does not exist
    }

    @Test
    public void getInstanceFromString() {
        // with trailing spaces
        String spacingBefore = " Sat, 31 October 2019";
        String spacingAfter = "Sat, 31 December 2019 ";
        // todo: should not throw
        //assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(spacingBefore));
        // assertThrows(IllegalValueException.class, () -> Date.getInstanceFromString(spacingAfter));
    }

    @Test
    public void getPreviousDate() {

    }

    @Test
    public void getNextDate() {

    }

    @Test
    public void toJavaDate() {

    }

    @Test
    public void fromJavaDate() {

    }

    @Test
    public void compareTo() {

    }
}

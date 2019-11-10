package seedu.billboard.ui.charts.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import seedu.billboard.ui.charts.converters.DayOfWeekConverter;


@SuppressWarnings("ResultOfMethodCallIgnored")
class DayOfWeekConverterTest {
    private TextStyle testStyle = TextStyle.FULL;
    private DayOfWeekConverter dayOfWeekConverter = new DayOfWeekConverter(testStyle);

    private String getTestFormatting(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(testStyle, Locale.getDefault());
    }

    @Test
    void toString_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dayOfWeekConverter.toString(null));
    }

    @Test
    void toString_invalidInput_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> dayOfWeekConverter.toString(-1));
        assertThrows(DateTimeException.class, () -> dayOfWeekConverter.toString(0));
        assertThrows(DateTimeException.class, () -> dayOfWeekConverter.toString(8));
        assertThrows(DateTimeException.class, () -> dayOfWeekConverter.toString(8.0001));
    }

    @Test
    void toString_validInput_returnsCorrectDayOfWeek() {
        assertEquals(getTestFormatting(DayOfWeek.MONDAY), dayOfWeekConverter.toString(1));
        assertEquals(getTestFormatting(DayOfWeek.SUNDAY), dayOfWeekConverter.toString(7));
        assertEquals(getTestFormatting(DayOfWeek.SUNDAY), dayOfWeekConverter.toString(7.001));
        assertEquals(getTestFormatting(DayOfWeek.FRIDAY), dayOfWeekConverter.toString(5.1));
        assertEquals(getTestFormatting(DayOfWeek.THURSDAY), dayOfWeekConverter.toString(4.99));
    }

    @Test
    void fromString_invalidInput_throwsExceptions() {
        assertThrows(NullPointerException.class, () -> dayOfWeekConverter.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> dayOfWeekConverter.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> dayOfWeekConverter.fromString("monDaY"));
        assertThrows(IllegalArgumentException.class, () -> dayOfWeekConverter.fromString("gibberish"));
    }

    @Test
    void fromString_validInput_returnsCorrectIntValue() {
        assertEquals(1, dayOfWeekConverter.fromString("Monday"));
        assertEquals(3, dayOfWeekConverter.fromString("Wednesday"));
        assertEquals(7, dayOfWeekConverter.fromString("Sunday"));
    }

    @Test
    void toStringAndFromStringCalledInSuccession_returnsSameInput() {
        int expected1 = 4;
        assertEquals(expected1, dayOfWeekConverter.fromString(dayOfWeekConverter.toString(4)));
        int expected2 = 1;
        assertEquals(expected2, dayOfWeekConverter.fromString(dayOfWeekConverter.toString(1)));
    }
}

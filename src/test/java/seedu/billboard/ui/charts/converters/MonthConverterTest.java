package seedu.billboard.ui.charts.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import seedu.billboard.ui.charts.converters.MonthConverter;


@SuppressWarnings("ResultOfMethodCallIgnored")
class MonthConverterTest {
    private TextStyle testStyle = TextStyle.FULL;
    private final MonthConverter standardConverter =
            new MonthConverter(LocalDate.of(2019, 12, 1), testStyle);

    private String getTestFormatting(Month month) {
        return month.getDisplayName(testStyle, Locale.getDefault());
    }

    @Test
    void toString_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> standardConverter.toString(null));
    }

    @Test
    void toString_invalidInput_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> standardConverter.toString(-59));
    }

    @Test
    void toString_validInput_returnsExpectedOutput() {
        assertEquals(getTestFormatting(Month.JANUARY), standardConverter.toString(0));
        assertEquals(getTestFormatting(Month.JANUARY), standardConverter.toString(54));
        assertEquals(getTestFormatting(Month.JANUARY), standardConverter.toString(4.4));
        assertEquals(getTestFormatting(Month.DECEMBER), standardConverter.toString(53.9));
        assertEquals(getTestFormatting(Month.FEBRUARY), standardConverter.toString(58.5));
        assertEquals(getTestFormatting(Month.APRIL), standardConverter.toString(14));

        MonthConverter offsetConverter =
                new MonthConverter(LocalDate.of(2019, 4, 13), testStyle);
        assertEquals(getTestFormatting(Month.MAY), offsetConverter.toString(0));
        assertEquals(getTestFormatting(Month.JUNE), offsetConverter.toString(4.5));
        assertEquals(getTestFormatting(Month.APRIL), offsetConverter.toString(53.9));
        assertEquals(getTestFormatting(Month.OCTOBER), offsetConverter.toString(26));
        assertEquals(getTestFormatting(Month.JANUARY), offsetConverter.toString(40.49));
    }

    @Test
    void fromString_invalidInput_throwsExceptions() {
        assertThrows(NullPointerException.class, () -> standardConverter.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> standardConverter.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> standardConverter.fromString("DecembeR"));
        assertThrows(IllegalArgumentException.class, () -> standardConverter.fromString("owijvwnoi"));
    }

    @Test
    void fromString_validInput_returnsCorrectIntValue() {
        assertEquals(0.0, standardConverter.fromString("January"));
        assertNotEquals(0.01, standardConverter.fromString("January"));
        assertEquals(4.5, standardConverter.fromString("February"));
        assertEquals(49.5, standardConverter.fromString("December"));

        MonthConverter offsetConverter =
                new MonthConverter(LocalDate.of(2019, 7, 13), testStyle);
        assertEquals(0.0, offsetConverter.fromString("August"));
        assertEquals(49.5, offsetConverter.fromString("July"));
        assertEquals(9.0, offsetConverter.fromString("October"));
    }

    @Test
    void toStringAndFromStringCalledInSuccession_returnsExpectedInput() {
        double expected1 = 4.5;
        assertEquals(expected1, standardConverter.fromString(standardConverter.toString(4.5)));
        double expected2 = 0.0;
        assertEquals(expected2, standardConverter.fromString(standardConverter.toString(1)));
        double expected3 = 49.5;
        assertEquals(expected3, standardConverter.fromString(standardConverter.toString(53.521)));
    }
}

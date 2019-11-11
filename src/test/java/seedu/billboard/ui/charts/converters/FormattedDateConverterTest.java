package seedu.billboard.ui.charts.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("ResultOfMethodCallIgnored")
class FormattedDateConverterTest {
    private DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    private FormattedDateConverter dateConverter = new FormattedDateConverter(formatter);

    private String getTestFormatting(LocalDate date) {
        return formatter.format(date);
    }

    @Test
    void toString_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dateConverter.toString(null));
    }

    @Test
    void toString_validInput_returnsCorrectDayOfWeek() {
        assertEquals(getTestFormatting(LocalDate.EPOCH), dateConverter.toString(0));
        assertEquals(getTestFormatting(LocalDate.of(1969, 12, 31)),
                dateConverter.toString(-1));
        assertEquals(getTestFormatting(LocalDate.of(2019, 11, 11)),
                dateConverter.toString(18211));
    }

    @Test
    void fromString_invalidInput_throwsExceptions() {
        assertThrows(NullPointerException.class, () -> dateConverter.fromString(null));
        assertThrows(DateTimeParseException.class, () -> dateConverter.fromString(""));
        assertThrows(DateTimeParseException.class, () -> dateConverter.fromString("12115"));
        assertThrows(DateTimeParseException.class, () -> dateConverter.fromString("december"));
    }

    @Test
    void fromString_validInput_returnsCorrectIntValue() {
        assertEquals(LocalDate.now().toEpochDay(), dateConverter.fromString("20191111"));
        assertEquals(LocalDate.of(1972, 12, 5).toEpochDay(),
                dateConverter.fromString("19721205"));
        assertEquals(LocalDate.of(2103, 5, 2).toEpochDay(),
                dateConverter.fromString("21030502"));
    }
}

package seedu.billboard.ui.charts;

import javafx.util.StringConverter;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * StringConverter to convert between a numerical value representing a day of week and the day of week's name.
 */
public class DayOfWeekConverter extends StringConverter<Number> {
    private TextStyle textStyle;

    /**
     * Initializes a {@code DayOfWeekConverter} with the given text style, which formats the output day of week
     * name.
     *
     * @param textStyle Given text style for day of week formatting.
     */
    public DayOfWeekConverter(TextStyle textStyle) {
        this.textStyle = textStyle;
    }

    @Override
    public String toString(Number number) {
        return DayOfWeek.of(number.intValue()).getDisplayName(textStyle, Locale.getDefault());
    }

    @Override
    public Number fromString(String string) {
        return DayOfWeek.valueOf(string).getValue();
    }
}

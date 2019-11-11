package seedu.billboard.ui.charts.converters;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Objects;

import javafx.util.StringConverter;

/**
 * StringConverter to convert between a numerical value representing a day of week and the day of week's name.
 */
public class DayOfWeekConverter extends StringConverter<Number> {
    private final TextStyle textStyle;
    private final Locale locale;
    private final Map<String, DayOfWeek> namesMap;

    /**
     * Initializes a {@code DayOfWeekConverter} with the given text style, which formats the output day of week
     * name.
     *
     * @param textStyle Given text style for day of week formatting.
     */
    public DayOfWeekConverter(TextStyle textStyle) {
        this.textStyle = textStyle;
        this.locale = Locale.getDefault();
        this.namesMap = new HashMap<>();

        for (var dayOfWeek : DayOfWeek.values()) {
            namesMap.put(dayOfWeek.getDisplayName(textStyle, locale), dayOfWeek);
        }
    }

    @Override
    public String toString(Number number) {
        Objects.requireNonNull(number);
        return DayOfWeek.of(number.intValue()).getDisplayName(textStyle, locale);
    }

    @Override
    public Number fromString(String string) {
        Objects.requireNonNull(string);
        DayOfWeek dayOfWeek = namesMap.get(string);
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("DayOfWeek does not exist for string: " + string);
        }
        return dayOfWeek.getValue();
    }
}

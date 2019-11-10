package seedu.billboard.ui.charts.converters;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javafx.util.StringConverter;

/**
 * StringConverter to convert between a numerical value representing a week in a year, and the name of the month that
 * contains it. Takes into account a date offset.
 */
public class MonthConverter extends StringConverter<Number> {

    private final TextStyle textStyle;
    private final Locale locale;
    private final LocalDate dateOffset;
    private final Map<String, Month> namesMap;

    /**
     * Initializes a {@code MonthConverter} with the given date offset, where the last month of the year will be the
     * same month as the offset. For example, if the date offset fall on February, the numerical value of 12 will map
     * to February and vice versa. Additionally takes in a {@code TextStyle} for formatting purposes.
     *
     * @param dateOffset Given date offset.
     * @param textStyle  Given text style for month name formatting.
     */
    public MonthConverter(LocalDate dateOffset, TextStyle textStyle) {
        this.textStyle = textStyle;
        this.locale = Locale.getDefault();
        this.dateOffset = dateOffset;
        this.namesMap = new HashMap<>();

        for (var month : Month.values()) {
            namesMap.put(month.getDisplayName(textStyle, locale), month);
        }
    }

    private int offsetNumberToMonthName(int monthPosition) {
        return (monthPosition + dateOffset.getMonthValue()) % 12 + 1;
    }

    private int offsetMonthNameToNumber(int monthNameValue) {
        return (monthNameValue - dateOffset.getMonthValue() + 11) % 12;
    }

    @Override
    public String toString(Number number) {
        Objects.requireNonNull(number);
        // 54 weeks / 12 months = 4.5 weeks per month
        return Month.of(offsetNumberToMonthName((int) Math.floor(number.doubleValue() / 4.5)))
                .getDisplayName(textStyle, locale);
    }

    @Override
    public Number fromString(String string) {
        Objects.requireNonNull(string);
        Month month = namesMap.get(string);
        if (month == null) {
            throw new IllegalArgumentException("Month does not exist for string: " + string);
        }
        return offsetMonthNameToNumber(month.getValue()) * 4.5;
    }
}

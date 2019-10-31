package seedu.billboard.ui.charts;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * StringConverter to convert between a numerical value representing a month and the months name. Takes into account a
 * date offset.
 */
public class MonthConverter extends StringConverter<Number> {
    private LocalDate dateOffset;
    private TextStyle textStyle;

    /**
     * Initializes a {@code MonthConverter} with the given date offset, where the last month of the year will be the
     * same month as the offset. For example, if the date offset fall on February, the numerical value of 12 will map
     * to February and vice versa. Additionally takes in a {@code TextStyle} for formatting purposes.
     *
     * @param dateOffset Given date offset.
     * @param textStyle  Given text style for month name formatting.
     */
    MonthConverter(LocalDate dateOffset, TextStyle textStyle) {
        this.dateOffset = dateOffset;
        this.textStyle = textStyle;
    }

    private int offsetNumberToMonthName(int monthPosition) {
        return (monthPosition + dateOffset.getMonthValue()) % 12 + 1;
    }

    private int offsetMonthNameToNumber(int monthNameValue) {
        return (monthNameValue - dateOffset.getMonthValue() + 12) % 12 - 1;
    }

    @Override
    public String toString(Number number) {
        return Month.of(offsetNumberToMonthName((int) Math.ceil(number.doubleValue() / 4.5)))
                .getDisplayName(textStyle, Locale.getDefault());
    }

    @Override
    public Number fromString(String string) {
        return offsetMonthNameToNumber(Month.valueOf(string).getValue());
    }
}

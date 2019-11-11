package seedu.billboard.ui.charts.converters;

import java.math.BigDecimal;

import javafx.util.StringConverter;

/**
 * StringConverter to convert between a number and the truncated string representation of that number. If the number's
 * string representation is longer than a certain specified length, the number will be formatted in scientific notation
 * instead.
 */
public class TruncatedNumberConverter extends StringConverter<Number> {
    private static final int MAX_CHARS = 19; // Quintillion dollars :)
    @Override
    public String toString(Number number) {
        String untruncated = number.toString();
        return untruncated.length() > MAX_CHARS
                ? String.format("%.2E", number.doubleValue())
                : untruncated;
    }

    @Override
    public Number fromString(String string) {
        return new BigDecimal(string);
    }
}

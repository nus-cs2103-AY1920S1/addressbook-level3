package seedu.billboard.ui.charts.converters;

import javafx.util.StringConverter;

import java.math.BigDecimal;

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

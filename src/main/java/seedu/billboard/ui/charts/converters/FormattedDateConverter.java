package seedu.billboard.ui.charts.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javafx.util.StringConverter;


/**
 * StringConverter to convert between the numerical value of an unix Epoch time, and the formatted string
 * representation of that date. Formats the string representation according to the provided {@code DateTimeFormatter}.
 */
public class FormattedDateConverter extends StringConverter<Number> {

    private DateTimeFormatter formatter;

    public FormattedDateConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String toString(Number number) {
        Objects.requireNonNull(number);
        return LocalDate.ofEpochDay(number.longValue()).format(formatter);
    }

    @Override
    public Number fromString(String string) {
        Objects.requireNonNull(string);
        return LocalDate.parse(string, formatter).toEpochDay();
    }

    /**
     * Sets the formatter to be used for the dates converted by this converter.
     * @param formatter Formatter to be used.
     */
    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
}

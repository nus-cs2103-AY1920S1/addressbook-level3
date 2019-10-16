package seedu.billboard.model.expense;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Expense's created dateTime in the Billboard.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class CreatedDateTime {

    // More formats can be added
    public static final List<String> ACCEPTABLE_PATTERNS = List.of("d/M/yyyy[ HHmm]");

    public static final String MESSAGE_CONSTRAINTS = "Created date should follow one of these formats:\n"
             + String.join("\n", ACCEPTABLE_PATTERNS);

    private static final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:kk a");


    public final LocalDateTime dateTime;

    public CreatedDateTime(String dateString) {
        requireNonNull(dateString);
        dateTime = tryParse(dateString);
    }

    /**
     *  Convenience constructor to create the date time directly from a {@code LocalDateTime}.
     */
    public CreatedDateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        this.dateTime = dateTime;
    }

    /**
     * Attempts to parse the given date string based on the acceptable formats.
     * @param dateString Input date string
     * @return The parsed LocalDateTime, if successful.
     * @throws IllegalArgumentException if the date string does not match the accepted formats.
     */
    private static LocalDateTime tryParse(String dateString) throws IllegalArgumentException {
        List<DateTimeFormatter> acceptableFormats = ACCEPTABLE_PATTERNS.stream()
                .map(DateTimeFormatter::ofPattern)
                .collect(Collectors.toList());

        for (DateTimeFormatter formatter : acceptableFormats) {
            try {
                TemporalAccessor accessor = formatter.parseBest(dateString, LocalDateTime::from, LocalDate::from);
                if (accessor instanceof LocalDateTime) {
                    return (LocalDateTime) accessor;
                } else if (accessor instanceof LocalDate) {
                    return ((LocalDate) accessor).atStartOfDay();
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }

        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if the given string is a valid date under the acceptable formats.
     */
    public static boolean isValidDate(String test) {
        try {
            tryParse(test);
            return true;
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreatedDateTime // instanceof handles nulls
                && dateTime.equals(((CreatedDateTime) other).dateTime)); // state check
    }

    @Override
    public String toString() {
        return dateTime.format(outputFormat);
    }
}

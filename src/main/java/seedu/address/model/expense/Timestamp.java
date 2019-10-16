package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Optional;

/**
 * Represents an Expense's timestamp in the address book.
 * Guarantees: immutable; is valid as declared in {@link #createTimestampIfValid(String)}
 */
public class Timestamp implements Comparable<Timestamp> {

    public static final int CURRENT_YEAR = LocalDate.now().getYear();

    public static final String MESSAGE_CONSTRAINTS_DATE =
            "Timestamps must be in the format dd-MM[-yyyy]";

    public static final String MESSAGE_CONSTRAINTS_PERIOD =
            "Input period is not week/month/year";

    private static final DateTimeFormatter FORMATTER_WITH_YEAR = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter FORMATTER_WITHOUT_YEAR =
            new DateTimeFormatterBuilder()
                    .appendPattern("dd-MM")
                    .parseDefaulting(ChronoField.YEAR, CURRENT_YEAR)
                    .toFormatter(Locale.ENGLISH);

    private static final int MONTH_CHANGE = 1;

    public final LocalDate timestamp;

    public Timestamp(LocalDate timestamp) {
        requireAllNonNull(timestamp);
        this.timestamp = timestamp;
    }

    /**
     * Constructs a Timestamp from a raw date String,
     * only if the date conforms to the format of dd-MM[-yyyy].
     *
     * @param rawTimestamp A String inputted by the user in the date field.
     * @return An Optional Timestamp that will contain a Timestamp
     * if the date given is of the valid format.
     */
    public static Optional<Timestamp> createTimestampIfValid(String rawTimestamp) {
        try {
            return Optional.of(new Timestamp(LocalDate.parse(rawTimestamp, FORMATTER_WITHOUT_YEAR)));
        } catch (DateTimeParseException e) {
            try {
                return Optional.of(new Timestamp(LocalDate.parse(rawTimestamp, FORMATTER_WITH_YEAR)));
            } catch (DateTimeParseException e1) {
                return Optional.empty();
            }
        }
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public boolean isBefore(Timestamp other) {
        return this.timestamp.isBefore(other.timestamp);
    }

    public boolean isAfter(Timestamp other) {
        return this.timestamp.isAfter(other.timestamp);
    }

    public Timestamp createBackwardTimestamp() {
        return new Timestamp(this.timestamp.minusMonths(MONTH_CHANGE));
    }

    public Timestamp createForwardTimestamp() {
        return new Timestamp(this.timestamp.plusMonths(MONTH_CHANGE));
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(LocalDate.now());
    }

    public Timestamp plus(Period period) {
        return new Timestamp(timestamp.plus(period));
    }

    public boolean isEqual(Timestamp startDate) {
        return timestamp.isEqual(startDate.getTimestamp());
    }

    @Override
    public String toString() {
        return timestamp.format(FORMATTER_WITH_YEAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timestamp // instanceof handles nulls
                && timestamp.equals(((Timestamp) other).timestamp)); // state check
    }

    @Override
    public int hashCode() {
        return timestamp.hashCode();
    }

    @Override
    public int compareTo(Timestamp other) {
        if (this.timestamp.isBefore(other.timestamp)) {
            return -1;
        }
        if (this.timestamp.isAfter(other.timestamp)) {
            return 1;
        }
        return 0;
    }
}

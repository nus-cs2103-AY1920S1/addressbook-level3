package seedu.moolah.model.expense;

import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.moolah.model.Timekeeper;
import seedu.moolah.model.budget.BudgetPeriod;

/**
 * Represents an Expense's timestamp in the MooLah.
 * Guarantees: immutable; is valid as declared in {@link #createTimestampIfValid(String)}
 */
public class Timestamp implements Comparable<Timestamp> {

    public static final int CURRENT_YEAR = LocalDate.now().getYear();

    public static final String MESSAGE_CONSTRAINTS_PERIOD =
            "Input period is not day/week/month/year";

    public static final String MESSAGE_CONSTRAINTS_GENERAL =
            "Input is not recognised!";

    public static final Timestamp EARLIEST_TIMESTAMP = new Timestamp(
            LocalDate.of(2000, 1, 1).atStartOfDay());

    private static final DateTimeFormatter FORMATTER_WITH_YEAR =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private static final DateTimeFormatter FORMATTER_WITHOUT_TIME = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final Pattern DDMM_PATTERN =
            Pattern.compile("(?<=\\b)(?<dd>[0-9]{1,2})(?<div1>[\\\\\\-\\/])(?<mm>[0-9]{1,2})");

    public final LocalDateTime fullTimestamp;

    public Timestamp(LocalDateTime fullTimestamp) {
        requireAllNonNull(fullTimestamp);
        this.fullTimestamp = fullTimestamp;
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
        Matcher m = DDMM_PATTERN.matcher(rawTimestamp);
        if (m.find()) {
            rawTimestamp = m.replaceFirst("$3$2$1");
        }
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(rawTimestamp);
            DateGroup group = groups.get(0);
            Date datetime = group.getDates().get(0);
            LocalDateTime fullTimestamp = Timekeeper.convertToLocalDateTime(datetime);
            return Optional.of(new Timestamp(fullTimestamp));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /**
     * Constructs a Timestamp from a raw date String,
     * only if the date conforms to the format of dd-MM[-yyyy].
     *
     * @param rawTimestamp A String inputted by the user in the date field.
     * @return An Optional Timestamp that will contain a Timestamp
     * if the date given is of the valid format.
     */
    public static Optional<Timestamp> createPastTimestampFromStorage(String rawTimestamp) {
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(rawTimestamp);
            DateGroup group = groups.get(0);
            Date datetime = group.getDates().get(0);
            LocalDateTime fullTimestamp = Timekeeper.convertToLocalDateTime(datetime);
            LocalDateTime currentTimestamp = getCurrentTimestamp().getFullTimestamp();
            if (fullTimestamp.isAfter(currentTimestamp)) {
                return Optional.empty();
            }

            return Optional.of(new Timestamp(fullTimestamp));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /**
     * Constructs a Timestamp from a raw date String,
     * only if the date conforms to the format of dd-MM[-yyyy].
     *
     * @param rawTimestamp A String inputted by the user in the date field.
     * @return An Optional Timestamp that will contain a Timestamp
     * if the date given is of the valid format.
     */
    public static Optional<Timestamp> createGeneralTimestampFromStorage(String rawTimestamp) {
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(rawTimestamp);
            DateGroup group = groups.get(0);
            Date datetime = group.getDates().get(0);
            LocalDateTime fullTimestamp = Timekeeper.convertToLocalDateTime(datetime);

            return Optional.of(new Timestamp(fullTimestamp));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public LocalDateTime getFullTimestamp() {
        return fullTimestamp;
    }

    public LocalDate getDate() {
        return fullTimestamp.toLocalDate();
    }

    public LocalDate getDateJustNow() {
        return fullTimestamp.minusSeconds(10).toLocalDate();
    }

    public Timestamp toStartOfDay() {
        return new Timestamp(fullTimestamp.toLocalDate().atStartOfDay());
    }

    public Timestamp toEndOfDay() {
        return new Timestamp(fullTimestamp.toLocalDate().atTime(LocalTime.MAX));
    }

    public boolean isBefore(Timestamp other) {
        return this.fullTimestamp.isBefore(other.fullTimestamp);
    }

    public boolean isAfter(Timestamp other) {
        return this.fullTimestamp.isAfter(other.fullTimestamp);
    }

    public boolean dateIsAfter(Timestamp other) {
        return this.fullTimestamp.toLocalDate().isAfter(other.fullTimestamp.toLocalDate());
    }

    public boolean dateIsBefore(Timestamp other) {
        return this.fullTimestamp.toLocalDate().isBefore(other.fullTimestamp.toLocalDate());
    }


    public Timestamp createBackwardTimestamp(BudgetPeriod period) {
        return new Timestamp(this.fullTimestamp.minus(period.getPeriod()));
    }

    /**
     * Finds a time behind the current timestamp by a few iteration of periods
     * @param period Period of an interval
     * @param number Number of iterations
     * @return A new timestamp
     */
    public Timestamp createBackwardTimestamp(BudgetPeriod period, int number) {
        Timestamp result = this;
        for (int i = 0; i < number; i++) {
            result = result.createBackwardTimestamp(period);
        }
        return result;
    }

    public Timestamp createForwardTimestamp(BudgetPeriod period) {
        return new Timestamp(this.fullTimestamp.plus(period.getPeriod()));
    }


    /**
     * Finds a time ahead of the current timestamp by a few iteration of periods
     * @param period Period of an interval
     * @param number Number of iterations
     * @return A new timestamp
     */
    public Timestamp createForwardTimestamp(BudgetPeriod period, int number) {
        Timestamp result = this;
        for (int i = 0; i < number; i++) {
            result = result.createForwardTimestamp(period);
        }
        return result;
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(LocalDateTime.now());
    }

    public Timestamp plus(Period period) {
        return new Timestamp(fullTimestamp.plus(period));
    }

    public Timestamp plusDays(long numDays) {
        return new Timestamp(fullTimestamp.plusDays(numDays));
    }

    public Timestamp minusDays(long numDays) {
        return new Timestamp(fullTimestamp.minusDays(numDays));
    }

    public int getDayOfMonth() {
        return fullTimestamp.getDayOfMonth();
    }

    public int getDayOfYear() {
        return fullTimestamp.getDayOfYear();
    }

    public int getMonthValue() {
        return fullTimestamp.getMonthValue();
    }

    public boolean isEqual(Timestamp startDate) {
        return fullTimestamp.isEqual(startDate.getFullTimestamp());
    }

    @Override
    public String toString() {
        return fullTimestamp.format(FORMATTER_WITH_YEAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timestamp // instanceof handles nulls
                && fullTimestamp.equals(((Timestamp) other).fullTimestamp)); // state check
    }

    @Override
    public int hashCode() {
        return fullTimestamp.hashCode();
    }

    @Override
    public int compareTo(Timestamp other) {
        if (this.fullTimestamp.isBefore(other.fullTimestamp)) {
            return -1;
        }
        if (this.fullTimestamp.isAfter(other.fullTimestamp)) {
            return 1;
        }
        return 0;
    }

    /**
     * Compare method that compares the Date without including time
     * @param other Another timestamp
     */
    public int compareDateTo(Timestamp other) {
        if (this.dateIsBefore(other)) {
            return -1;
        }
        if (this.dateIsAfter(other)) {
            return 1;
        }
        return 0;
    }

    /**
     * Method to format a Timestamp object to show only date dd-MM-YYYY
     * @return Date in correct format
     */
    public String showDate() {
        LocalDate date = this.getDate();
        return date.format(FORMATTER_WITHOUT_TIME);
    }


}

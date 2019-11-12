package seedu.moolah.model.general;

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

import seedu.moolah.logic.Timekeeper;
import seedu.moolah.model.budget.BudgetPeriod;

/**
 * Represents an Expense's timestamp in the MooLah.
 * Guarantees: immutable; is valid as declared in {@link #createTimestampIfValid(String)}
 */
public class Timestamp implements Comparable<Timestamp> {

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
     * Constructs a Timestamp from a raw date String given by the user.
     *
     * @param rawTimestamp A String inputted by the user in the date field.
     * @return An Optional Timestamp that will contain a Timestamp
     * if the datetime given is of the valid format.
     */
    public static Optional<Timestamp> createTimestampIfValid(String rawTimestamp) {
        Matcher m = DDMM_PATTERN.matcher(rawTimestamp);
        if (m.find()) {
            // changes International datetime format to American so that Natty can parse the input properly
            rawTimestamp = m.replaceFirst("$3$2$1");
        }
        return parseRawTimestamp(rawTimestamp);
    }

    /**
     * Returns an Optional timestamp from parsing a string through the Natty parser.
     *
     * @param rawTimestamp The raw timestamp input.
     * @return An Optional that contains a timestamp if the raw timestamp input was successfully parsed by Natty.
     */
    private static Optional<Timestamp> parseRawTimestamp(String rawTimestamp) {
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(rawTimestamp); // Natty parses the datetime input
            DateGroup group = groups.get(0);
            Date datetime = group.getDates().get(0);
            LocalDateTime fullTimestamp = Timekeeper.convertToLocalDateTime(datetime);
            return Optional.of(new Timestamp(fullTimestamp));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /**
     * Constructs a Timestamp from storage, only if the timestamp is still in the past compared to system time.
     *
     * @param rawTimestamp A String inputted by the user in the date field.
     * @return An Optional Timestamp that will contain a Timestamp
     * if the datetime is valid and it is in the past.
     */
    public static Optional<Timestamp> createPastTimestampFromStorage(String rawTimestamp) {
        Optional<Timestamp> potentialTimestamp = parseRawTimestamp(rawTimestamp);
        if (potentialTimestamp.isPresent()) {
            Timestamp timestamp = potentialTimestamp.get();
            Timestamp currentTimestamp = getCurrentTimestamp();
            return timestamp.isAfter(currentTimestamp) ? Optional.empty() : Optional.of(timestamp);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Constructs a Timestamp from storage.
     *
     * @param rawTimestamp A String inputted by the user in the date field.
     * @return An Optional Timestamp that will contain a Timestamp
     * if the datetime is valid.
     */
    public static Optional<Timestamp> createGeneralTimestampFromStorage(String rawTimestamp) {
        return parseRawTimestamp(rawTimestamp);
    }

    /**
     * Returns the full timestamp.
     *
     * @return A LocalDateTime object representing the full timestamp.
     */
    public LocalDateTime getFullTimestamp() {
        return fullTimestamp;
    }

    /**
     * Returns the timestamp accurate to date.
     *
     * @return A LocalDate object representing the date of the timestamp.
     */
    public LocalDate getDate() {
        return fullTimestamp.toLocalDate();
    }

    /**
     * Returns the date just now.
     *
     * @return A LocalDate object that represents the date 10 seconds ago.
     */
    public LocalDate getDateJustNow() {
        return fullTimestamp.minusSeconds(10).toLocalDate();
    }

    /**
     * Moves this timestamp to the start of day.
     *
     * @return A Timestamp with same date but 00:00:00 time.
     */
    public Timestamp toStartOfDay() {
        return new Timestamp(fullTimestamp.toLocalDate().atStartOfDay());
    }

    /**
     * Moves this timestamp to the end of day.
     *
     * @return A Timestamp with same date but 23:59:59 time.
     */
    public Timestamp toEndOfDay() {
        return new Timestamp(fullTimestamp.toLocalDate().atTime(LocalTime.MAX));
    }

    /**
     * Checks if this timestamp is before another.
     *
     * @param other The other timestamp to compare.
     * @return True if this timestamp is before the other timestamp, false otherwise.
     */
    public boolean isBefore(Timestamp other) {
        return this.fullTimestamp.isBefore(other.fullTimestamp);
    }

    /**
     * Checks if this timestamp is after another.
     *
     * @param other The other timestamp to compare.
     * @return True if this timestamp is after the other timestamp, false otherwise.
     */
    public boolean isAfter(Timestamp other) {
        return this.fullTimestamp.isAfter(other.fullTimestamp);
    }

    /**
     * Checks if this timestamp's date is after another timestamp's date.
     *
     * @param other The other timestamp to compare.
     * @return True if this timestamp's date is after the other timestamp's date, false otherwise.
     */
    public boolean dateIsAfter(Timestamp other) {
        return this.fullTimestamp.toLocalDate().isAfter(other.fullTimestamp.toLocalDate());
    }

    /**
     * Checks if this timestamp's date is before another timestamp's date.
     *
     * @param other The other timestamp to compare.
     * @return True if this timestamp's date is before the other timestamp's date, false otherwise.
     */
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
     * Finds a time ahead of the current timestamp by a few iteration of periods.
     * @param period Period of an interval.
     * @param number Number of iterations.
     * @return A new timestamp.
     */
    public Timestamp createForwardTimestamp(BudgetPeriod period, int number) {
        Timestamp result = this;
        for (int i = 0; i < number; i++) {
            result = result.createForwardTimestamp(period);
        }
        return result;
    }

    /**
     * Gets the current system time.
     *
     * @return A Timestamp that represents the current system time.
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(LocalDateTime.now());
    }

    /**
     * Plus a period to this timestamp.
     *
     * @param period The specified period to add to this timestamp.
     * @return A Timestamp that represents this timestamp after the specified period is added to it.
     */
    public Timestamp plus(Period period) {
        return new Timestamp(fullTimestamp.plus(period));
    }

    /**
     * Plus a number of days to this timestamp.
     *
     * @param numDays The specified number of days to add to this timestamp.
     * @return A Timestamp that represents this timestamp after the specified number of days is added to it.
     */
    public Timestamp plusDays(long numDays) {
        return new Timestamp(fullTimestamp.plusDays(numDays));
    }

    /**
     * Minus a number of days from this timestamp.
     *
     * @param numDays The specified number of days to minus from this timestamp.
     * @return A Timestamp that represents this timestamp after the specified number of days is subtracted from it.
     */
    public Timestamp minusDays(long numDays) {
        return new Timestamp(fullTimestamp.minusDays(numDays));
    }

    /**
     * Gets the day of month from this timestamp.
     *
     * @return A integer that represents the day of month.
     */
    public int getDayOfMonth() {
        return fullTimestamp.getDayOfMonth();
    }

    /**
     * Gets the day of year from this timestamp.
     *
     * @return A integer that represents the day of year.
     */
    public int getDayOfYear() {
        return fullTimestamp.getDayOfYear();
    }

    /**
     * Gets the month value from this timestamp.
     *
     * @return A integer that represents the month.
     */
    public int getMonthValue() {
        return fullTimestamp.getMonthValue();
    }

    /**
     * Returns a string representation of this timestamp.
     *
     * @return A string that represents this timestamp, including the year.
     */
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
     * Compare method that compares the Date without including time.
     * @param other Another timestamp.
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
     * Method to format a Timestamp object to show only date dd-MM-yyyy.
     * @return Date in dd-MM-yyyy format.
     */
    public String showDate() {
        LocalDate date = this.getDate();
        return date.format(FORMATTER_WITHOUT_TIME);
    }


}

package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the date of an expense in the MYMorise.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Date should be of the format Hmm or HHmm or dd/MM/yyyy Hmm or "
        + "d/MM/yyyy Hmm or dd/MM/yyyy or d/MM/yyyy";
    public static final String HMM_REGEX = "\\d{3}";
    public static final String HHMM_REGEX = "\\d{4}";
    public static final String DMYYYY_REGEX = "[\\d]{1}[/][\\d]{1}[/][\\d]{4}";
    public static final String DMMYYYY_REGEX = "[\\d]{1}[/][\\d]{2}[/][\\d]{4}";
    public static final String DDMYYYY_REGEX = "[\\d]{2}[/][\\d]{1}[/][\\d]{4}";
    public static final String DDMMYYYY_REGEX = "[\\d]{2}[/][\\d]{2}[/][\\d]{4}";
    public static final String DMYYYYHMM_REGEX = "[\\d]{1}[/][\\d]{1}[/][\\d]{4}\\s[\\d]{3}";
    public static final String DMMYYYYHMM_REGEX = "[\\d]{1}[/][\\d]{2}[/][\\d]{4}\\s[\\d]{3}";
    public static final String DDMYYYYHMM_REGEX = "[\\d]{2}[/][\\d]{1}[/][\\d]{4}\\s[\\d]{3}";
    public static final String DDMMYYYYHMM_REGEX = "[\\d]{2}[/][\\d]{2}[/][\\d]{4}\\s[\\d]{3}";
    public static final String DMYYYYHHMM_REGEX = "[\\d]{1}[/][\\d]{1}[/][\\d]{4}\\s[\\d]{4}";
    public static final String DMMYYYYHHMM_REGEX = "[\\d]{1}[/][\\d]{2}[/][\\d]{4}\\s[\\d]{4}";
    public static final String DDMYYYYHHMM_REGEX = "[\\d]{2}[/][\\d]{1}[/][\\d]{4}\\s[\\d]{4}";
    public static final String DDMMYYYYHHMM_REGEX = "[\\d]{2}[/][\\d]{2}[/][\\d]{4}\\s[\\d]{4}";

    public final String value;
    public final String rawValue;
    public final LocalDate localDate;

    /**
     * Constructs an {@code Date}.
     */
    public Date() {
        rawValue = null;
        value = null;
        localDate = null;
    }

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        rawValue = date;
        value = convertDate(date);
        localDate = value.length() > 11
            ? LocalDate.parse(value, DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mma"))
            : LocalDate.parse(value, DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    /**
     * Constructs an {@code Date}.
     *
     * @param rawDate     A valid rawDate.
     * @param ifConverted Status to determine whether to convert the date
     */
    public Date(String rawDate, boolean ifConverted) {
        requireNonNull(rawDate);
        checkArgument(isValidDate(rawDate), MESSAGE_CONSTRAINTS);
        rawValue = rawDate;
        value = ifConverted ? convertDate(rawDate) : rawDate;
        localDate = null;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return !test.equals(convertDate(test));
    }

    /**
     * Transform a given string to a date
     * Supported format: Hmm, HHmm, d/M/yyyy, d/MM/yyyy, dd/M/yyyy, dd/MM/yyyy,
     * d/M/yyyy Hmm, d/MM/yyyy Hmm, dd/M/yyyy Hmm, dd/MM/yyyy Hmm,
     * d/M/yyyy HHmm, d/MM/yyyy HHmm, dd/M/yyyy HHmm, dd/MM/yyyy HHmm,
     * e.g. 1/12/2019, 1/12/2019 1845, 10/12/2019, 10/12/2019 1845, 1245
     *
     * @param date given string
     * @return converted date
     */
    private static String convertDate(String date) {
        DateTimeFormatter formatter;
        LocalTime newTime;
        LocalDate newDate;
        LocalDateTime newDateTime;
        String datePattern = "dd MMM yyyy";
        String dateTimePattern = "dd MMM yyyy, hh:mma";
        if (date.matches(HMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("Hmm");
            newTime = LocalTime.parse(date, formatter);
            return appendDate(newTime).format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(HHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("Hmm");
            newTime = LocalTime.parse(date, formatter);
            return appendDate(newTime).format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DMYYYY_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            newDate = LocalDate.parse(date, formatter);
            return newDate.format(DateTimeFormatter.ofPattern(datePattern));
        } else if (date.matches(DMMYYYY_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            newDate = LocalDate.parse(date, formatter);
            return newDate.format(DateTimeFormatter.ofPattern(datePattern));
        } else if (date.matches(DDMYYYY_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
            newDate = LocalDate.parse(date, formatter);
            return newDate.format(DateTimeFormatter.ofPattern(datePattern));
        } else if (date.matches(DDMMYYYY_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            newDate = LocalDate.parse(date, formatter);
            return newDate.format(DateTimeFormatter.ofPattern(datePattern));
        } else if (date.matches(DMYYYYHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("d/M/yyyy Hmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DMMYYYYHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("d/MM/yyyy Hmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DDMYYYYHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("dd/M/yyyy Hmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DDMMYYYYHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy Hmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DMYYYYHHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DMMYYYYHHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HHmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DDMYYYYHHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("dd/M/yyyy HHmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else if (date.matches(DDMMYYYYHHMM_REGEX)) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            newDateTime = LocalDateTime.parse(date, formatter);
            return newDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern));
        } else {
            return date;
        }
    }

    private static LocalDateTime appendDate(LocalTime time) {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.of(time.getHour(), time.getMinute());
        return LocalDateTime.of(localDate, localTime);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Date // instanceof handles nulls
            && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

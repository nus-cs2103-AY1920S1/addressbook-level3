//@@author dalsontws

package seedu.address.model.deadline;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline's Due Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_CONSTRAINTS = "Invalid Deadline is entered!\n"
            + "Deadlines are to be in dd/MM/yyyy format and be a valid date.\n"
            + "E.g.: 12/01/2019.";
    public static final String EARLY_DATE = "Date provided is before today.\n"
            + "Please provided a due date in a later date.";

    private static LocalDate today = LocalDate.now();
    private final String dateStr;
    private final LocalDate localDate;

    /**
     * Constructs a {@code Question}.
     *
     * @param deadline A valid DueDate.
     *                 Check if its a valid formatted date
     *                 Also, ensure date is not earlier then present day
     */
    public DueDate(String deadline) {
        requireNonNull(deadline);
        this.dateStr = deadline;
        checkArgument(isValidDate(deadline), MESSAGE_CONSTRAINTS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(deadline, formatter);
        this.localDate = date;
    }

    /**
     * Returns true if a given string is in a valid format
     * i.e. dd/MM/yyyy
     */
    public static boolean isValidDate(String test) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(test, dateFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        String[] split = test.split("/");
        int day = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);
        
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                return false;
            }
        }
        if (year % 4 == 0) {
            if (month == 2) {
                if (day > 29) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Returns LocalDate format of DueDate
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Returns true if a given date is today or after today
     */
    public static boolean isLaterDate(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate testDate = LocalDate.parse(test, formatter);
        Period difference = Period.between(testDate, today);
        if (difference.getYears() < 1 && difference.getMonths() < 1 && difference.getDays() < 1) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public String toString() {
        return this.dateStr;
    }

    @Override
    public int hashCode() {
        return dateStr.hashCode();
    }
}

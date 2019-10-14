package seedu.address.model.Employee;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Event Starting Date.
 */
public class EmployeeJoinDate {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String MESSAGE_CONSTRAINTS =
            "Start Date Needed should be an in the following format dd/MM/yyyy";

    public final LocalDate joinDate;

    /**
     * Constructs a {@code EventStartDate}.
     *
     * @param joinDate A valid start date.
     */
    public EmployeeJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * Returns true if a given string is a valid localDate number.
     */
    public static boolean isValidJoinDate(String test) {
        try {
            return LocalDate.parse(test, FORMATTER) instanceof LocalDate;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return joinDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeJoinDate // instanceof handles nulls
                && joinDate.equals(((EmployeeJoinDate) other).joinDate)); // state check
    }

    @Override
    public int hashCode() {
        return joinDate.hashCode();
    }
}

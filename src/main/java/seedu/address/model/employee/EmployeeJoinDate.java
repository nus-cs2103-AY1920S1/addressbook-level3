package seedu.address.model.employee;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Employee Join Date.
 */
public class EmployeeJoinDate {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public final LocalDate joinDate;

    /**
     * Constructs a {@code EmployeeJoinDate}.
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
            return ParserUtil.parseAnyDate(test) instanceof LocalDate;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return joinDate.format(FORMATTER);
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

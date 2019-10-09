package seedu.address.model.deadline;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Represents a Deadline's Due Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_CONSTRAINTS = "Deadlines are of dd/MM/yyyy Format.\n"
            + "E.g.: 12/01/2019.";

    //private static LocalDate today = LocalDate.now();
    public static String dateStr;
    private static LocalDate localDate;

    /**
     * Constructs a {@code Question}.
     *
     * @param deadline A valid question.
     */
    public DueDate(String deadline) {
        this.dateStr = deadline;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(deadline, formatter);
        requireNonNull(deadline);
        checkArgument(isValidDate(deadline), MESSAGE_CONSTRAINTS);
        this.localDate = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(test.trim());
        } catch (java.text.ParseException pe) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return dateStr;
    }

    @Override
    public int hashCode() {
        return dateStr.hashCode();
    }

}

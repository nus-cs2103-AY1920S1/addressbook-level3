package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an assignment's deadline in the classroom.
 * Guarantees: immutable
 */
public class AssignmentDeadline {

    public static final String MESSAGE_CONSTRAINTS =
            "There is no such date / time! Input should be in dd/MM/yyyy HHmm format.";

    public final String assignmentDeadline;
    private Calendar assignmentDeadlineCalendar;

    /**
     * Constructs a {@code AssignmentDeadline}.
     *
     * @param assignmentDeadline A valid assignment deadline.
     */
    public AssignmentDeadline(String assignmentDeadline) {
        requireNonNull(assignmentDeadline);
        this.assignmentDeadline = assignmentDeadline;
        this.assignmentDeadlineCalendar = setAssignmentDeadlineCalendar();
    }

    private Calendar setAssignmentDeadlineCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = null;
        try {
            date = sdf.parse(assignmentDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar assignmentDeadlineCalendar = Calendar.getInstance();
        assignmentDeadlineCalendar.setTime(date);
        return assignmentDeadlineCalendar;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentDeadline // instanceof handles nulls
                && assignmentDeadline.equals(((AssignmentDeadline) other).assignmentDeadline)); // state check
    }

    @Override
    public int hashCode() {
        return assignmentDeadline.hashCode();
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy h:mm a");
        return formatter.format(assignmentDeadlineCalendar.getTime());
    }
}

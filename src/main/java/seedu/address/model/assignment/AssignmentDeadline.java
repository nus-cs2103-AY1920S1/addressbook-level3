package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//@@author weikiat97
/**
 * Represents an assignment's deadline in the classroom.
 * Guarantees: immutable
 */
public class AssignmentDeadline {

    public static final String MESSAGE_CONSTRAINTS =
            "There is no such date / time! Input should be in dd/MM/yyyy HHmm format.";
    public static final String MESSAGE_DEADLINE_CONSTRAINT =
            "Deadline inputted should not be before the current system time.";

    public final String assignmentDeadline;
    private Calendar assignmentDeadlineCalendar;

    /**
     * Constructs a {@code AssignmentDeadline}.
     *
     * @param assignmentDeadline A valid assignment deadline.
     */
    public AssignmentDeadline(String assignmentDeadline) {
        requireNonNull(assignmentDeadline);
        checkArgument(isValidAssignmentDeadline(assignmentDeadline), MESSAGE_CONSTRAINTS);
        this.assignmentDeadline = assignmentDeadline;
        this.assignmentDeadlineCalendar = setAssignmentDeadlineCalendar();
    }

    /**
     * Gets the AssignmentDeadline in Calendar format.
     * @return Calendar representing the assignment deadline.
     */
    public Calendar getAssignmentDeadlineCalendar() {
        return this.assignmentDeadlineCalendar;
    }

    /**
     * Returns true if a given string is a valid assignment deadline.
     */
    public static boolean isValidAssignmentDeadline(String test) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        sdf.setLenient(false);
        try {
            sdf.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Formats the assignment deadline into a Calendar format.
     * @return Calendar representation of the assignment deadline.
     */
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

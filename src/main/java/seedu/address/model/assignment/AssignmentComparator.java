package seedu.address.model.assignment;

import java.util.Comparator;

//@@author weikiat97
/**
 * Sorts the assignment list by chronological deadline.
 */
public class AssignmentComparator implements Comparator<Assignment> {

    @Override
    public int compare(Assignment assignment1, Assignment assignment2) {
        if (assignment1.getAssignmentDeadline().getAssignmentDeadlineCalendar()
                .before(assignment2.getAssignmentDeadline().getAssignmentDeadlineCalendar())) {
            return -1;
        } else {
            return 1;
        }
    }

}

package seedu.tarence.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class AssignmentTest {
    public final String assignName = "Assignment Name";
    public final Integer maxScore = 1;
    public final Date startDate = new Date(0);
    public final Date endDate = new Date();

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Assignment(null, null, null, null));
    }

    @Test
    public void invalidAssignName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Assignment("", maxScore, startDate, endDate),
                Assignment.MESSAGE_CONSTRAINTS_ASSIGNMENT_NAME);
    }

    @Test
    public void invalidMaxScore_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Assignment(assignName, -1, startDate, endDate),
                Assignment.MESSAGE_CONSTRAINTS_ASSIGNMENT_NAME);
    }

    @Test
    public void invalidStartDateEndDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Assignment(assignName, maxScore, new Date(), new Date(0)),
                Assignment.MESSAGE_CONSTRAINTS_ASSIGNMENT_NAME);
    }

    @Test
    public void equals() {
        Assignment assignment = new Assignment(assignName,
                maxScore,
                startDate,
                endDate);
        // same object
        assertTrue(assignment.equals(assignment));

        // same data fields
        Assignment assignmentCopy = new Assignment(assignName,
                maxScore,
                startDate,
                endDate);
        assertTrue(assignment.equals(assignmentCopy));

        // different assignment name
        assertFalse(assignment.equals(new Assignment(assignName + "A",
                maxScore,
                startDate,
                endDate)));

        // different max score
        assertFalse(assignment.equals(new Assignment(assignName,
                maxScore + 1,
                startDate,
                endDate)));

        // different start date
        assertFalse(assignment.equals(new Assignment(assignName,
                maxScore,
                new Date(1),
                endDate)));

        // different end date
        assertFalse(assignment.equals(new Assignment(assignName,
                maxScore,
                startDate,
                new Date(1))));
    }

    @Test
    public void compareTo() {
        Assignment assignment1 = new Assignment(
                assignName,
                maxScore,
                startDate,
                endDate);
        Assignment assignment2 = new Assignment(
                assignName,
                maxScore,
                startDate,
                endDate);
        // same data fields
        assertTrue(assignment1.compareTo(assignment2) == 0);

        // comparison based on end date
        assertTrue(assignment1.compareTo(
                new Assignment(assignName,
                maxScore,
                startDate,
                new Date(1))) > 0);

        // comparison based on start date
        assertTrue(assignment1.compareTo(
                new Assignment(assignName,
                maxScore,
                new Date(1),
                endDate)) < 0);

        // comparison based on max score - no effect
        assertTrue(assignment1.compareTo(
                new Assignment(assignName,
                maxScore + 1,
                startDate,
                endDate)) == 0);

        // comparison based on assignment name
        assertTrue(assignment1.compareTo(
                new Assignment(assignName + "a",
                maxScore,
                startDate,
                endDate)) < 0);
    }
}

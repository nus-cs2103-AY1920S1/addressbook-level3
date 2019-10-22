package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Map;

/**
 * Represents an assignment in the classroom.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assignment {

    public static final String MESSAGE_CONSTRAINTS = "Assignment names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final AssignmentName assignmentName;
    public final AssignmentGrades assignmentGrades;
    private boolean isCompleted;

    /**
     * Constructs a {@code Assignment}.
     *
     * @param assignmentName A valid assignment name.
     */
    public Assignment(AssignmentName assignmentName) {
        requireNonNull(assignmentName);
        this.assignmentName = assignmentName;
        this.assignmentGrades = new AssignmentGrades();
        this.isCompleted = false;
    }

    public AssignmentName getAssignmentName() {
        return this.assignmentName;
    }

    public Map<String, Integer> getGrades() {
        return this.assignmentGrades.getGrades();
    }

    /**
     * Parses a {@code List<String> grades} and {@code List<Integer> newGrades} to update assignmentGrades.
     *
     */
    public void setGrades(List<String> studentNames, List<Integer> newGrades) {
        requireAllNonNull(studentNames, newGrades);
        this.assignmentGrades.setGrades(studentNames, newGrades);
        this.isCompleted = true;
    }


    /**
     * Parses a {@code String studentName} into an {@code Assignment}.
     * Returns the updated assignment after removing key-value pair for key: studentName.
     */
    public Assignment deleteOneStudentGrade (String studentName) {
        assignmentGrades.deleteOneStudentGrade(studentName);
        return this;
    }

    public List<Integer> marksStringListFromGrades() {
        return assignmentGrades.marksStringListFromGrades();
    }

    public List<String> namesStringListFromGrades() {
        return assignmentGrades.namesStringListFromGrades();
    }

    public String gradesMapToString() {
        return assignmentGrades.toString();
    }

    /**
     * Returns true if both assignments of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
                   && otherAssignment.getAssignmentName().equals(getAssignmentName());
    }

    @Override
    public String toString() {
        String output = "";
        output += this.assignmentName;
        output += "\nCompleted: " + this.isCompleted;
        output += "\n" + this.assignmentGrades;
        return output;
    }
}

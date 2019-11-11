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
    public final AssignmentDeadline assignmentDeadline;
    private boolean isCompleted;

    /**
     * Constructs a {@code Assignment}.
     *
     * @param assignmentName A valid assignment name.
     */
    public Assignment(AssignmentName assignmentName, AssignmentDeadline assignmentDeadline) {
        requireNonNull(assignmentName);
        this.assignmentName = assignmentName;
        this.assignmentGrades = new AssignmentGrades();
        this.assignmentDeadline = assignmentDeadline;
        this.isCompleted = false;
    }

    /**
     * Initialises the grades for students as "Not submitted" for new assignments
     * @param studentNames students name to serve as keys during initialisation.
     */
    public void initialiseGrades(List<String> studentNames) {
        requireNonNull(studentNames);
        for (String studentName : studentNames) {
            assignmentGrades.addOneUncompletedStudentGrade(studentName);
        }
    }

    public AssignmentName getAssignmentName() {
        return this.assignmentName;
    }

    public AssignmentDeadline getAssignmentDeadline() {
        return this.assignmentDeadline;
    }

    public Map<String, String> getGrades() {
        return this.assignmentGrades.getGrades();
    }

    /**
     * Parses a {@code List<String> grades} and {@code List<String> newGrades} to update assignmentGrades.
     *
     */
    public void setGrades(List<String> studentNames, List<String> newGrades) {
        requireAllNonNull(studentNames, newGrades);
        this.assignmentGrades.setGrades(studentNames, newGrades);
        //this.isCompleted = true;
        checkCompletion();
    }

    /**
     * Checks if the given assignment is completed from the assignment grades map.
     */
    private void checkCompletion() {
        isCompleted = true;
        for (String value : getGrades().values()) {
            if (value.equals("Not submitted.")) {
                isCompleted = false;
            }
        }
    }

    /**
     * Parses a {@code String studentName} into an {@code Assignment}.
     * Returns the updated assignment after adding key-value pair for key: studentName.
     * Value is set to "Not submitted" as student was added before assignment was graded.
     */
    public void addNewStudentGrade (String studentName) {
        assignmentGrades.addOneUncompletedStudentGrade(studentName);
        setGrades(this.namesStringListFromGrades(), this.marksStringListFromGrades());
    }

    /**
     * Parses a {@code String studentName} into an {@code Assignment}.
     * Returns the updated assignment after adding key-value pair for key: studentName.
     * Value is set to "Late to the party" as student was added after assignment was graded.
     */
    public void addOneStudentGrade (String studentName) {
        assignmentGrades.addOneCompletedStudentGrade(studentName);
        setGrades(this.namesStringListFromGrades(), this.marksStringListFromGrades());
    }


    /**
     * Parses a {@code String studentName} into an {@code Assignment}.
     * Returns the updated assignment after removing key-value pair for key: studentName.
     */
    public Assignment deleteOneStudentGrade (String studentName) {
        assignmentGrades.deleteOneStudentGrade(studentName);
        Assignment outputAssignment = new Assignment (this.assignmentName, this.assignmentDeadline);
        outputAssignment.setGrades(this.namesStringListFromGrades(), this.marksStringListFromGrades());
        return outputAssignment;
    }

    public List<String> marksStringListFromGrades() {
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
        output += this.getAssignmentName();
        output += "\n";
        output += this.getAssignmentDeadline();
        if (this.isCompleted) {
            output += "\nCompleted: ";
        } else {
            output += "\nNot Completed: ";
        }

        output += "\n" + this.assignmentGrades;
        return output;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void setCompletionStatus(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}

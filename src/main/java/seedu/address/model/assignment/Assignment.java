package seedu.address.model.assignment;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assignment in the classroom.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assignment {

    public static final String MESSAGE_CONSTRAINTS = "Assignment names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final AssignmentName assignmentName;
    private boolean isCompleted;
    private HashMap<String, Integer> assignmentGrades = new HashMap<>();

    /**
     * Constructs a {@code Assignment}.
     *
     * @param assignmentName A valid assignment name.
     */
    public Assignment(AssignmentName assignmentName) {
        requireNonNull(assignmentName);
        this.assignmentName = assignmentName;
        this.isCompleted = false;
    }

    public AssignmentName getAssignmentName() {
        return this.assignmentName;
    }

    public Set<Integer> getGrades() {
        return new HashSet<>(assignmentGrades.values());
    }

    public void setGrades(List<String> studentNames, List<Integer> newGrades) {
        //Remove grades of students that no longer exist in the classroom
        for (String existingName: assignmentGrades.keySet()) {
            boolean shouldInclude = false;
            for (String updatedName: studentNames) {
                if (updatedName.equals(existingName)) {
                    shouldInclude = true;
                }
            }
            if (!shouldInclude) {
                assignmentGrades.remove(existingName);
            }
        }
        //Add new grades
        for (int i = 0; i < studentNames.size(); i++) {
            String studentName = studentNames.get(i);
            Integer studentGrade = newGrades.get(i);
            if (assignmentGrades.containsKey(studentName)) {
                assignmentGrades.replace(studentName, studentGrade);
            } else {
                assignmentGrades.put(studentName, studentGrade);
            }
        }
        this.isCompleted = true;

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

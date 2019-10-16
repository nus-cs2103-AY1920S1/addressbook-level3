package seedu.address.model.assignment;

import java.util.HashMap;
import java.util.List;

/**
 * Represents an assignment in the classroom.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assignment {
    private String assignmentName;
    private boolean isCompleted;
    private HashMap<String, Integer> assignmentGrades = new HashMap<>();

    public Assignment(String name) {
        this.assignmentName = name;
        this.isCompleted = false;
    }

    public String getAssignmentName() {
        return this.assignmentName;
    }

    public HashMap<String, Integer> getGrades() {
        return this.assignmentGrades;
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

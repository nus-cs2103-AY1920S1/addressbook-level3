package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import seedu.address.model.student.Student;



/**
 * Represents an assignment in the classroom.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assignment {

    private final AssignmentName assignmentName;
    private boolean isCompleted;
    private LinkedHashMap<Student, Grade> assignmentGrades = new LinkedHashMap<>();

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

    public LinkedList<Grade> getGrades() {
        return new LinkedList<>(assignmentGrades.values());
    }

    public void setGrades(List<Student> studentList, List<Grade> newGrades) {
        //Remove grades of students that no longer exist in the classroom
        for (Student existingStudent: assignmentGrades.keySet()) {
            boolean shouldInclude = false;
            for (Student updatedStudent: studentList) {
                if (updatedStudent.equals(existingStudent)) {
                    shouldInclude = true;
                }
            }
            if (!shouldInclude) {
                assignmentGrades.remove(existingStudent);
            }
        }
        //Add new grades
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            Grade studentGrade;
            if (newGrades.size() <= i) {
                studentGrade = new Grade("-1");
            } else {
                studentGrade = newGrades.get(i);
            }
            if (assignmentGrades.containsKey(student)) {
                assignmentGrades.replace(student, studentGrade);
            } else {
                assignmentGrades.put(student, studentGrade);
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
        output += this.getAssignmentName();
        output += "\nCompleted: " + this.isCompleted;
        output += "\n" + this.assignmentGrades;
        return output;
    }
}

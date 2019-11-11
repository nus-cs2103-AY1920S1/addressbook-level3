package seedu.address.model.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a AssignmentGrades in the classroom.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String[])}
 */
public class AssignmentGrades {

    public static final String MESSAGE_CONSTRAINTS =
            "Marks should only contain numeric characters between 0 to 100, and it should not be blank";

    public static final String SINGLE_ASSIGNMENT_MESSAGE_CONSTRAINTS =
            "There should be only one mark when editing an individual student's marks, which should only contain "
                    + "numeric characters between 0 to 100, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String VALIDATION_REGEX = "100|\\d{1,2}";

    private HashMap<String, String> assignmentGrades;

    /**
     * Constructs a {@code AssignmentGrades}.
     */
    public AssignmentGrades() {
        assignmentGrades = new LinkedHashMap<>();
    }

    public Map<String, String> getGrades() {
        return this.assignmentGrades;
    }

    /**
     * Parses a {@code List<String> grades} and {@code List<Integer> newGrades} to update assignmentGrades.
     * A LinkedHashMap is created with key-value pairs, with keys from grades and values from newGrades
     */
    public void setGrades(List<String> studentNames, List<String> newGrades) {
        //Add new grades
        for (int i = 0; i < studentNames.size(); i++) {
            String studentName = studentNames.get(i);
            String studentGrade = newGrades.get(i);
            if (assignmentGrades.containsKey(studentName)) {
                assignmentGrades.replace(studentName, studentGrade);
            } else {
                assignmentGrades.put(studentName, studentGrade);
            }
        }
    }

    /**
     * Parses a {@code String studentName}.
     * Adds the key-value pair for key: studentName, value: Not submitted.
     */
    public void addOneUncompletedStudentGrade (String studentName) {
        assignmentGrades.put(studentName, "Not submitted.");
    }

    /**
     * Parses a {@code String studentName}.
     * Adds the key-value pair for key: studentName, value: Late to the party.
     */
    public void addOneCompletedStudentGrade (String studentName) {
        assignmentGrades.put(studentName, "Late to the party.");
    }

    /**
     * Parses a {@code String studentName}.
     * Removes the key-value pair for key: studentName.
     */
    public void deleteOneStudentGrade (String studentName) {
        assignmentGrades.remove(studentName);
    }


    /**
     * Returns a {@code List<String> names} created from keys in LinkedHashMap assignmentGrades
     */
    public List<String> namesStringListFromGrades() {
        List<String> names = new ArrayList<>();
        names.addAll(assignmentGrades.keySet());
        return names;
    }

    /**
     * Returns a {@code List<Integer> marks} created from values in LinkedHashMap assignmentGrades
     */
    public List<String> marksStringListFromGrades() {
        List<String> marks = new ArrayList<>();
        for (String key:assignmentGrades.keySet()) {
            marks.add(assignmentGrades.get(key));
        }
        return marks;
    }

    /**
     * Returns true if a given array of string is a valid AssignmentGrades map.
     */
    public static boolean isValidGrade(String[] marks) {
        for (String mark: marks) {
            if (!mark.matches(VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a given string is a valid AssignmentGrades map.
     */
    public static boolean isValidGrade(String mark) {
        if (!mark.matches(VALIDATION_REGEX)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        String gradeOutput = "";
        List<String> names = this.namesStringListFromGrades();
        List<String> marks = this.marksStringListFromGrades();
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            String mark = marks.get(i);
            gradeOutput += name + ": " + mark + "\n";
        }
        return gradeOutput;
    }

}


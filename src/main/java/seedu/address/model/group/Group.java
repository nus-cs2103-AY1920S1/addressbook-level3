package seedu.address.model.group;

import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;

import java.util.ArrayList;

public class Group {
    private String groupId;
    private UniqueStudentList studentList;

    /**
     * Creates a Group instance with the appropriate attributes.
     * @param groupId The identifier of the group, in String representation.
     */
    public Group(String groupId) {
        this.groupId = groupId;
        this.studentList = new UniqueStudentList();
    }

    /**
     * Returns a String representation of the quiz identifier.
     * @return The String representation of the quiz identifier.
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * Adds a student to the student list of a group.
     * @param student The student to be added to the student list of the group.
     * @return True if the student is not a repeat, else false.
     */
    public boolean addStudent(Student student) {
        return studentList.add(student);
    }

    /**
     * Adds a student to the student list of a group.
     * @param groupIndexNumber The group index number to add the student to.
     * @param student The student to be added to the student list of the group.
     * @return True if the student is not a repeat, else false.
     */
    public boolean addStudent(int groupIndexNumber, Student student) {
        return studentList.add(groupIndexNumber, student);
    }

    /**
     * Removes a student from the student list of a group.
     * @param groupIndexNumber The student to be removed from the student list of the group.
     * @return The removed question.
     */
    public Student removeStudent(int groupIndexNumber) {
        return studentList.remove(groupIndexNumber);
    }

    /**
     * Returns the students, formatted in String representation for writing to a text file.
     * @return The students, formatted in String representation for writing to the text file.
     */
    public String getStudentsFormatted() {
       return studentList.getStudentList();
    }

}

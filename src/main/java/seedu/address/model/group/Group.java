package seedu.address.model.group;

import javafx.collections.ObservableList;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;
import seedu.address.storage.export.WordDocExporter;

/**
 * Model that represents a group of students.
 */
public class Group {
    private String groupId;
    private UniqueStudentList studentList;

    /**
     * Creates a Group instance with only the groupId.
     *
     * @param groupId The identifier of the group, in String representation.
     */
    public Group(String groupId) {
        this.groupId = groupId;
        this.studentList = new UniqueStudentList();
    }

    /**
     * Creates a group instance with both the groupId and the list of students.
     *
     * @param groupId     Identifier of the group.
     * @param studentList List of students.
     */
    public Group(String groupId, UniqueStudentList studentList) {
        this.groupId = groupId;
        this.studentList = studentList;
    }

    /**
     * Returns a String representation of the group identifier.
     *
     * @return The String representation of the group identifier.
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * Adds a student to the student list of a group.
     *
     * @param student The student to be added to the student list of the group.
     * @return True if the student is not a repeat, else false.
     */
    public boolean addStudent(Student student) {
        return studentList.add(student);
    }

    /**
     * Adds a student to the student list of a group.
     *
     * @param groupIndexNumber The group index number to add the student to.
     * @param student          The student to be added to the student list of the group.
     * @return True if the student is not a repeat, else false.
     */
    public boolean addStudent(int groupIndexNumber, Student student) {
        return studentList.add(groupIndexNumber, student);
    }

    /**
     * Removes a student from the student list of a group.
     *
     * @param groupIndexNumber The student to be removed from the student list of the group.
     * @return The removed question.
     */
    public Student removeStudent(int groupIndexNumber) {
        return studentList.remove(groupIndexNumber);
    }

    /**
     * Returns the students, formatted in String representation for writing to a text file.
     *
     * @return The students, formatted in String representation for writing to the text file.
     */
    public String getStudentsFormatted() {
        return studentList.getStudentList();
    }

    /**
     * Returns the observable list of students
     *
     * @return The students in an observable list.
     */
    public ObservableList<Student> getObservableListStudents() {
        return studentList.asUnmodifiableObservableList();
    }

    /**
     * Exports the group into a word document
     */
    public void export() {
        String studentsFormatted = this.getStudentsFormatted();
        WordDocExporter.saveExport(this.groupId, studentsFormatted);
    }

    /**
     * Check if student exists in group.
     *
     * @param student Student to check
     * @return True if student exists in the group.
     */
    public boolean checkStudentExist(Student student) {
        Name name = student.getName();
        for (Student checkStudent : studentList) {
            if (checkStudent.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the unique list of students from the group.
     *
     * @return Unique list of students in the group.
     */
    public UniqueStudentList getStudentList() {
        return this.studentList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getGroupId().equals(this.getGroupId());
    }
}

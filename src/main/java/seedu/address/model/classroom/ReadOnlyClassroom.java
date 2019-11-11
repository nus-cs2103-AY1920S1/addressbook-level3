package seedu.address.model.classroom;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
//import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

import java.util.List;

/**
 * Unmodifiable view of a classroom.
 */
public interface ReadOnlyClassroom {

    /**
     * Gets the name of the classroom
     */
    String getClassroomName();

    /**
     * Sets the name of the classroom
     */
    void setClassroomName(String classroomName);

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

    /**
     * returns unmodifiable view of assignment list
     */
    ObservableList<Assignment> getAssignmentList();

    /**
     * Returns an list of student names.
     * This list will not contain any duplicate students.
     */
    List<String> getStudentNameList();

}

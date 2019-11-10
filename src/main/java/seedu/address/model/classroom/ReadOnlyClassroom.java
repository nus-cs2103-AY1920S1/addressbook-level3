package seedu.address.model.classroom;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
//import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * Unmodifiable view of an classroom.
 */
public interface ReadOnlyClassroom {

    /**
     * Returns the name of the classroom
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

    /**
     * returns unmodifiable view of assignment list
     */
    ObservableList<Assignment> getAssignmentList();

}

package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.lesson.Lesson;

/**
 * Unmodifiable view of a notebook.
 */
public interface ReadOnlyNotebook {
    /**
     * Returns an unmodifiable view of the classrooms list.
     * This list will not contain any duplicate classrooms.
     */
    ObservableList<Classroom> getClassroomList();

    /**
     * Returns an unmodifiable view of the lessons list.
     * This list will not contain any duplicate lessons.
     */
    ObservableList<Lesson> getLessonList();
}

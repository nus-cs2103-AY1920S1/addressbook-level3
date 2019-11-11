package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;

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
     * Returns the current classroom in the notebook.
     */
    Classroom getCurrentClassroom();

    /**
     * Returns an unmodifiable view of the lessons list in the week.
     * This list will not contain any duplicate lessons.
     */
    ObservableList<UniqueLessonList> getLessonWeekList();

    /**
     * Returns an unmodefiable view of the lessons list in the day.
     * This list will not contain any duplicate lessons..
     */
    ObservableList<Lesson> getLessonList();
}

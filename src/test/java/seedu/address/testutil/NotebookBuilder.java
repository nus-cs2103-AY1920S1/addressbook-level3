package seedu.address.testutil;

import java.util.List;

import seedu.address.model.Notebook;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.lesson.UniqueLessonList;

/**
 * A utility class to help with building Notebook objects.
 * Example usage: <br>
 *     {@code Notebook nb = new NotebookBuilder().withClassrooms(classrooms).withLessons(lessons).build();}
 */
public class NotebookBuilder {

    private Notebook notebook;

    public NotebookBuilder() {
        notebook = new Notebook();
    }

    public NotebookBuilder(Notebook notebook) {
        this.notebook = notebook;
    }

    /**
     * Adds a list of new {@code Classsrooms} to the {@code Notebook} that we are building.
     */
    public NotebookBuilder withClassrooms(List<Classroom> classrooms) {
        notebook.setClassrooms(classrooms);
        return this;
    }

    /**
     * Adds a list of new {@code Lessons} to the {@code Notebook} that we are building.
     */
    public NotebookBuilder withLessons(List<UniqueLessonList> lessons) {
        notebook.setAllLessons(lessons);
        return this;
    }


    public Notebook build() {
        return notebook;
    }
}

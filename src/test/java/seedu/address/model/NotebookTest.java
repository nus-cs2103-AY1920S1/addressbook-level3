package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClassrooms.CLASSROOM_ONE;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;
import static seedu.address.testutil.TypicalStudents.getTypicalStudents;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.exceptions.DuplicateClassroomException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.testutil.ClassroomBuilder;

//@@author weikiat97
public class NotebookTest {

    private final Notebook notebook = new Notebook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), notebook.getClassroomList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> notebook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNotebook_replacesData() {
        Notebook newData = getTypicalNotebook();
        notebook.resetData(newData);
        assertEquals(newData, notebook);
    }


    @Test
    public void resetData_withDuplicateClassrooms_throwsDuplicateClassroomException() {
        // Two Classrooms with the same identity fields
        Classroom editedClassroomOne = new ClassroomBuilder(CLASSROOM_ONE).withClassroomName("Classroom One")
                .withStudents(getTypicalStudents()).build();
        List<Classroom> newClassrooms = Arrays.asList(CLASSROOM_ONE, editedClassroomOne);
        NotebookStub newData = new NotebookStub(newClassrooms);

        assertThrows(DuplicateClassroomException.class, () -> notebook.resetData(newData));
    }


    @Test
    public void hasClassroom_nullClassroom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> notebook.hasClassroom(null));
    }

    @Test
    public void hasClassroom_classroomNotInNotebook_returnsFalse() {
        assertFalse(notebook.hasClassroom(CLASSROOM_ONE));
    }

    @Test
    public void hasClassroom_classroomInNotebook_returnsTrue() {
        notebook.addClassroom(CLASSROOM_ONE);
        assertTrue(notebook.hasClassroom(CLASSROOM_ONE));
    }

    @Test
    public void hasClassroom_classroomWithSameIdentityFieldsInNotebook_returnsTrue() {
        notebook.addClassroom(CLASSROOM_ONE);
        Classroom editedClassroom = new ClassroomBuilder(CLASSROOM_ONE).withClassroomName("Classroom Two")
                .build();
        assertTrue(notebook.hasClassroom(editedClassroom));
    }

    @Test
    public void getClassroomList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> notebook.getClassroomList().remove(0));
    }

    /**
     * A stub ReadOnlyNotebook whose Classrooms list can violate interface constraints.
     */
    private static class NotebookStub implements ReadOnlyNotebook {
        private final ObservableList<Classroom> classrooms = FXCollections.observableArrayList();
        private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();
        private final ObservableList<UniqueLessonList> lessonList = FXCollections.observableArrayList();

        NotebookStub(Collection<Classroom> classrooms) {
            this.classrooms.setAll(classrooms);
        }

        @Override
        public ObservableList<Classroom> getClassroomList() {
            return classrooms;
        }

        @Override
        public Classroom getCurrentClassroom() {
            return null;
        }

        @Override
        public ObservableList<UniqueLessonList> getLessonWeekList() {
            return lessonList;
        }

        @Override
        public ObservableList<Lesson> getLessonList() {
            return lessons;
        }
    }

}

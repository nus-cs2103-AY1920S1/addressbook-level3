package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyNotebook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.student.Student;
import seedu.address.testutil.LessonBuilder;


public class AddLessonCommandTest {

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null));
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();
        Lesson validLesson = new LessonBuilder().build();

        CommandResult commandResult = new AddLessonCommand(validLesson).execute(modelStub);

        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLesson), modelStub.lessonsAdded);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson validLesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(validLesson);
        ModelStub modelStub = new ModelStubWithLesson(validLesson);

        assertThrows(CommandException.class, AddLessonCommand.MESSAGE_DUPLICATE_LESSON, (
        ) -> addLessonCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Lesson math = new LessonBuilder().withClassName("Math").build();
        Lesson eng = new LessonBuilder().withClassName("English").build();
        AddLessonCommand addMathCommand = new AddLessonCommand(math);
        AddLessonCommand addEngCommand = new AddLessonCommand(eng);

        // same object -> returns true
        assertTrue(addMathCommand.equals(addMathCommand));

        // same values -> returns true
        AddLessonCommand addMathCommandCopy = new AddLessonCommand(math);
        assertTrue(addMathCommand.equals(addMathCommandCopy));

        // different types -> returns false
        assertFalse(addMathCommand.equals(1));

        // null -> returns false
        assertFalse(addMathCommand.equals(null));

        // different student -> returns false
        assertFalse(addMathCommand.equals(addEngCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getNotebookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNotebookFilePath(Path classroomFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClassroom(ReadOnlyClassroom newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Classroom getCurrentClassroom() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Classroom getClassroom(Classroom classroom) {
            return null;
        }

        @Override
        public void setNotebook(ReadOnlyNotebook notebook) {

        }

        @Override
        public ReadOnlyNotebook getNotebook() {
            return null;
        }

        @Override
        public boolean hasClassroom(Classroom classroom) {
            return false;
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(Assignment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLesson(Lesson target) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteClassroom(Classroom target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignment(Assignment target, Assignment editedAssignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAllAssignmentsWithName(Student oldStudent, Student newStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLesson(Lesson target, Lesson editedLesson) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addClassroom(Classroom classroom) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentClassroom(Classroom classroom) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Classroom> getClassroomList() {
            return null;
        }

        @Override
        public ObservableList<Lesson> getLessonList() {
            return null;
        }

        @Override
        public void addLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean checkTimingExist(Lesson lesson) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assignment> getFilteredAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<UniqueLessonList> getFilteredLessonWeekList() {
            return null;
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateFilteredLessonWeekList(Predicate<UniqueLessonList> predicate) {

        }

        @Override
        public void displayAssignments() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void displayStudents() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public String displayLessons() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isDisplayStudents() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyNotebook undo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyNotebook redo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveState() {
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithLesson extends AddLessonCommandTest.ModelStub {
        private final Lesson lesson;

        ModelStubWithLesson(Lesson lesson) {
            requireNonNull(lesson);
            this.lesson = lesson;
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            requireNonNull(lesson);
            return this.lesson.isSameLesson(lesson);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingLessonAdded extends AddLessonCommandTest.ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();
        final UniqueLessonList lessons = new UniqueLessonList();

        @Override
        public boolean hasLesson(Lesson lesson) {
            requireNonNull(lesson);
            return lessonsAdded.stream().anyMatch(lesson::isSameLesson);
        }

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessonsAdded.add(lesson);
        }

        @Override
        public boolean checkTimingExist(Lesson lesson) {
            return lessons.checkTimingExist(lesson);
        }

    }
}

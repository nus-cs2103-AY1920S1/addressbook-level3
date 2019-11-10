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
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;

import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.StudentBuilder;

public class AddAssignmentCommandTest {

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssignmentCommand(null));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAssignmentAdded modelStub = new ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = new AssignmentBuilder().build();

        CommandResult commandResult = new AddAssignmentCommand(validAssignment).execute(modelStub);

        assertEquals(String.format(AddAssignmentCommand.MESSAGE_SUCCESS, validAssignment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Assignment validAssignment = new AssignmentBuilder().build();
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(validAssignment);
        ModelStub modelStub = new ModelStubWithAssignment(validAssignment);

        assertThrows(CommandException.class, AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT, (
        ) -> addAssignmentCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Assignment mathAssignment = new AssignmentBuilder().withAssignmentName("Math Homework 3").build();
        Assignment englishAssignment = new AssignmentBuilder().withAssignmentName("English Assignment 2").build();
        AddAssignmentCommand addMathAssignmentCommand = new AddAssignmentCommand(mathAssignment);
        AddAssignmentCommand addEnglishAssignmentCommand = new AddAssignmentCommand(englishAssignment);

        // same object -> returns true
        assertTrue(addMathAssignmentCommand.equals(addMathAssignmentCommand));

        // same values -> returns true
        AddAssignmentCommand addMathAssignmentCommandCopy = new AddAssignmentCommand(mathAssignment);
        assertTrue(addMathAssignmentCommand.equals(addMathAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addMathAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addMathAssignmentCommand.equals(null));

        // different Assignment -> returns false
        assertFalse(addMathAssignmentCommand.equals(addEnglishAssignmentCommand));
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
        public void setStudent(Student target, Student editedAssignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignment(Assignment target, Assignment editedAssignment) {
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
        public void displayStudents() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void displayAssignments() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isDisplayStudents() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public String displayLessons() {
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
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private final Assignment assignment;

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return this.assignment.isSameAssignment(assignment);
        }
    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();
        final UniqueAssignmentList assignments = new UniqueAssignmentList();
        final UniqueStudentList students = new UniqueStudentList();

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return assignmentsAdded.stream().anyMatch(assignment::isSameAssignment);
        }

        @Override
        public void addAssignment(Assignment assignment) {
            requireNonNull(assignment);
            assignmentsAdded.add(assignment);
        }

        @Override
        public Classroom getCurrentClassroom() {
            return new Classroom();
        }

        @Override
        public ObservableList<Assignment> getFilteredAssignmentList() {
            AssignmentName assignmentName = new AssignmentName("Dummy Assignment");
            AssignmentDeadline assignmentDeadline = new AssignmentDeadline("01/02/03 0456");
            Assignment dummyAssignment = new Assignment(assignmentName, assignmentDeadline);
            assignments.add(dummyAssignment);
            return assignments.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            Student student = new StudentBuilder().build();
            students.add(student);
            return students.asUnmodifiableObservableList();
        }

        @Override
        public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        }

        @Override
        public void setAssignment(Assignment target, Assignment editedAssignment) {
            throw new AssertionError("This method should not be called.");
        }
    }

}

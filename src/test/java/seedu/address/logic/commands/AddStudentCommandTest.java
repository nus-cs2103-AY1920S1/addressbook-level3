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
import seedu.address.testutil.StudentBuilder;

public class AddStudentCommandTest {

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddStudentCommand(validStudent).execute(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class, AddStudentCommand.MESSAGE_DUPLICATE_STUDENT, (
            ) -> addStudentCommand.execute(modelStub));
    }

    @Test
    public void execute_samePhoneAsParentPhone_throwsCommandException() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student invalidStudent = new StudentBuilder().withPhone("99999999").withParentPhone("99999999").build();

        assertThrows(CommandException.class, String.format(AddStudentCommand.MESSAGE_SAME_PHONE_AND_PARENT_PHONE,
                invalidStudent), () -> new AddStudentCommand(invalidStudent).execute(modelStub));

    }


    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();
        final UniqueAssignmentList assignments = new UniqueAssignmentList();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
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
        public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        }

        @Override
        public void setAssignment(Assignment target, Assignment editedAssignment) {
        }
    }

}

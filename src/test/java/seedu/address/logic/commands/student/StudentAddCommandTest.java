package seedu.address.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.student.StudentAddCommand.MESSAGE_DUPLICATE_STUDENT;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.student.StudentBuilder;

/**
 * Test for StudentAddCommand.
 */
public class StudentAddCommandTest {

    /**
     * Test for unsuccessfully adding a null student.
     */
    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentAddCommand(null));
    }

    /**
     * Test for successfully adding a student to the model.
     */
    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new StudentAddCommand(validStudent).execute(modelStub);

        assertEquals(String.format(StudentAddCommand.MESSAGE_SUCCESS, validStudent), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    /**
     * Test for unsuccessfully adding a duplicate student.
     */
    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        StudentAddCommand addCommand = new StudentAddCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);
        assertThrows(CommandException.class, () -> addCommand.execute(modelStub), MESSAGE_DUPLICATE_STUDENT);
    }

    /**
     * Tests if two StudentAddCommands are equal.
     */
    @Test
    public void equals() {
        Student student = new StudentBuilder().withName(new Name("Student")).build();
        Student otherStudent = new StudentBuilder().withName(new Name("Other Student")).build();
        StudentAddCommand addStudentCommand = new StudentAddCommand(student);
        StudentAddCommand addOtherStudentCommand = new StudentAddCommand(otherStudent);

        // same object -> returns true
        assertTrue(addStudentCommand.equals(addStudentCommand));

        // same values -> returns true
        StudentAddCommand addStudentCommandCopy = new StudentAddCommand(student);
        assertTrue(addStudentCommand.equals(addStudentCommandCopy));

        // different types -> returns false
        assertFalse(addStudentCommand.equals(1));

        // null -> returns false
        assertFalse(addStudentCommand.equals(null));

        // different note -> returns false
        assertFalse(addStudentCommand.equals(addOtherStudentCommand));
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        /**
         * Creates a ModelStubWithStudent instance.
         *
         * @param student Student to be added.
         */
        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        /**
         * Checks if the student has already been added.
         *
         * @param student Student to be checked
         * @return true if student has already been added, false otherwise.
         */
        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        /**
         * Checks if the student has already been added.
         *
         * @param student Student to be checked
         * @return true if student has already been added, false otherwise.
         */
        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        /**
         * Adds student to the ArrayList of students.
         *
         * @param student Student to be added.
         */
        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        /**
         * Gets the record of students.
         *
         * @return Student record.
         */
        @Override
        public StudentRecord getStudentRecord() {
            return new StudentRecord();
        }
    }
}

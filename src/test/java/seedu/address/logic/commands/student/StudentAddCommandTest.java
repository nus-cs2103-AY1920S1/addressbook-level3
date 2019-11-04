package seedu.address.logic.commands.student;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.student.StudentBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.student.StudentAddCommand.MESSAGE_DUPLICATE_STUDENT;

public class StudentAddCommandTest {
    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentAddCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new StudentAddCommand(validStudent).execute(modelStub);

        assertEquals(String.format(StudentAddCommand.MESSAGE_SUCCESS, validStudent), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        StudentAddCommand addCommand = new StudentAddCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);
        assertThrows(CommandException.class, () -> addCommand.execute(modelStub), MESSAGE_DUPLICATE_STUDENT);
    }

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
     * A Model stub that contains a single note.
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
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

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
        public StudentRecord getStudentRecord() {
            return new StudentRecord();
        }
    }
}

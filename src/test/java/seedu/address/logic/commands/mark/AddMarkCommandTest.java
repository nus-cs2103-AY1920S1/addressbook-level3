package seedu.address.logic.commands.mark;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.mark.AddMarkCommand.MESSAGE_STUDENT_ALREADY_MARKED;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.student.StudentBuilder;

/**
 * Test for AddMarkCommand.
 */
public class AddMarkCommandTest {


    /**
     * Tests whether two add mark commands are equal.
     */
    @Test
    public void equals() {
        AddMarkCommand addMarkCommand = new AddMarkCommand(Index.fromOneBased(1));
        AddMarkCommand otherAddMarkCommand = new AddMarkCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(addMarkCommand.equals(addMarkCommand));

        // same values -> returns true
        AddMarkCommand addMarkCommandCopy = new AddMarkCommand(Index.fromOneBased(1));
        assertTrue(addMarkCommand.equals(addMarkCommandCopy));

        // different types -> returns false
        assertFalse(addMarkCommand.equals(1));

        // null -> returns false
        assertFalse(addMarkCommand.equals(null));

        assertFalse(addMarkCommand.equals(otherAddMarkCommand));
    }

    /**
     * Test for adding a mark successfully.
     */
    @Test
    public void execute_addMark_success() throws Exception {
        AddMarkCommand addMarkCommand = new AddMarkCommand(Index.fromOneBased(1));
        Student student = new StudentBuilder().withName(new Name("MarkTest")).build();
        ModelStub modelStub = new ModelStubWithStudent(student);
        CommandResult commandResult = addMarkCommand.execute(modelStub);
        assertEquals(String.format(addMarkCommand.MESSAGE_SUCCESS, 1), commandResult.getFeedbackToUser());
    }

    /**
     * Test for adding a mark unsuccessfully, marking a student who has already been marked.
     */
    @Test
    public void execute_addMarkToMarkedStudent_throwsCommandException() throws Exception {
        AddMarkCommand addMarkCommand = new AddMarkCommand(Index.fromOneBased(1));
        Student student = new StudentBuilder().withName(new Name("AlreadyMarked")).withMark(true).build();
        ModelStub modelStub = new ModelStubWithStudent(student);
        assertThrows(CommandException.class, () -> addMarkCommand.execute(modelStub),
                String.format(MESSAGE_STUDENT_ALREADY_MARKED, 1));
    }

    /**
     * Test for adding a mark successfully, index of student is out of bounds of the student list.
     */
    @Test
    public void execute_addMarkOutOfBounds_throwsCommandException() throws Exception {
        AddMarkCommand addMarkCommand = new AddMarkCommand(Index.fromOneBased(2));
        Student student = new StudentBuilder().withName(new Name("OutOfBounds")).build();
        ModelStub modelStub = new ModelStubWithStudent(student);
        assertThrows(CommandException.class, () -> addMarkCommand.execute(modelStub),
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;
        private final StudentRecord studentRecord;
        private final FilteredList<Student> filteredStudents;

        /**
         * Creates a ModelStubWithStudent instance.
         *
         * @param student Student to be added.
         */
        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
            this.studentRecord = new StudentRecord();
            studentRecord.addStudent(student);
            this.filteredStudents = new FilteredList<>(this.studentRecord.getStudentList());
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

        /**
         * Gets a filtered list of the students stored.
         *
         * @return Filtered list of the students stored.
         */
        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return studentRecord.getStudentList();
        }

        /**
         * Replaces a student that already exists in the student record.
         *
         * @param target        Student to be edited.
         * @param editedStudent New Student.
         */
        @Override
        public void setStudent(Student target, Student editedStudent) {
            requireAllNonNull(target, editedStudent);
            studentRecord.setStudent(target, editedStudent);
        }

        /**
         * Updates the filtered list of students.
         *
         * @param predicate Predicate.
         */
        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            requireNonNull(predicate);
            filteredStudents.setPredicate(predicate);
        }

        /**
         * Gets the marked status of a student.
         *
         * @param student Student to be checked.
         * @return true if student has been marked, false otherwise.
         */
        @Override
        public boolean getIsMarked(Student student) {
            return student.getIsMarked();
        }

    }
}

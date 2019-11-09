package seedu.address.logic.commands.mark;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.mark.RemoveMarkCommand.MESSAGE_STUDENT_ALREADY_UNMARKED;

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
 * Test for RemoveMarkCommand.
 */
public class RemoveMarkCommandTest {

    /**
     * Tests if two RemoveMarkCommands are equal.
     */
    @Test
    public void equals() {
        RemoveMarkCommand removeMarkCommand = new RemoveMarkCommand(Index.fromOneBased(1));
        RemoveMarkCommand otherRemoveMarkCommand = new RemoveMarkCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(removeMarkCommand.equals(removeMarkCommand));

        // same values -> returns true
        RemoveMarkCommand removeMarkCommandCopy = new RemoveMarkCommand(Index.fromOneBased(1));
        assertTrue(removeMarkCommand.equals(removeMarkCommandCopy));

        // different types -> returns false
        assertFalse(removeMarkCommand.equals(1));

        // null -> returns false
        assertFalse(removeMarkCommand.equals(null));

        // different command -> returns false
        assertFalse(removeMarkCommand.equals(otherRemoveMarkCommand));
    }

    /**
     * Test for removing mark successfully.
     */
    @Test
    public void execute_removeMark_success() throws Exception {
        RemoveMarkCommand removeMarkCommand = new RemoveMarkCommand(Index.fromOneBased(1));
        Student student = new StudentBuilder().withName(new Name("UnmarkTest")).withMark(true).build();
        ModelStub modelStub = new RemoveMarkCommandTest.ModelStubWithStudent(student);
        CommandResult commandResult = removeMarkCommand.execute(modelStub);
        assertEquals(String.format(removeMarkCommand.MESSAGE_SUCCESS, 1), commandResult.getFeedbackToUser());
    }

    /**
     * Test for removing mark from a student who has not been marked.
     */
    @Test
    public void execute_removeMarkFromUnmarkedStudent_throwsCommandException() throws Exception {
        RemoveMarkCommand removeMarkCommand = new RemoveMarkCommand(Index.fromOneBased(1));
        Student student = new StudentBuilder().withName(new Name("AlreadyUnmarked")).withMark(false).build();
        ModelStub modelStub = new RemoveMarkCommandTest.ModelStubWithStudent(student);
        assertThrows(CommandException.class, () -> removeMarkCommand.execute(modelStub),
                String.format(MESSAGE_STUDENT_ALREADY_UNMARKED, 1));
    }

    /**
     * Test for removing mark with an out of bounds index.
     */
    @Test
    public void execute_removeMarkOutOfBounds_throwsCommandException() throws Exception {
        RemoveMarkCommand removeMarkCommand = new RemoveMarkCommand(Index.fromOneBased(2));
        Student student = new StudentBuilder().withName(new Name("RemoveOutOfBounds")).build();
        ModelStub modelStub = new RemoveMarkCommandTest.ModelStubWithStudent(student);
        assertThrows(CommandException.class, () -> removeMarkCommand.execute(modelStub),
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

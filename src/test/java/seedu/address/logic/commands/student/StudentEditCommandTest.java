package seedu.address.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.student.StudentEditCommand.MESSAGE_DUPLICATE_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THREE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.student.TypicalStudents.getTypicalStudentRecord;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.student.StudentEditCommand.EditStudentDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.student.StudentBuilder;

/**
 * Test for StudentEditCommand.
 */
public class StudentEditCommandTest {
    private Model model = new ModelManager();

    /**
     * Creates an instance of StudentEditCommandTest.
     */
    public StudentEditCommandTest() {
        model.setStudentRecord(getTypicalStudentRecord());
    }

    /**
     * Test for successfully editing a student.
     */
    @Test
    public void execute_editAllFields_success() {
        Student editedStudent = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptor();
        descriptor.setName(editedStudent.getName());

        StudentEditCommand editCommand = new StudentEditCommand(INDEX_ONE, descriptor);

        Model expectedModel = new ModelManager();
        expectedModel.setStudentRecord(getTypicalStudentRecord());
        Student toBeEdited = model.getFilteredStudentList().get(0);
        expectedModel.setStudent(toBeEdited, editedStudent);
        String expectedMessage = String.format(StudentEditCommand.MESSAGE_SUCCESS, toBeEdited, editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, CommandResultType.SHOW_STUDENT);
    }

    /**
     * Test for unsuccessfully editing a student to a duplicate student.
     */
    @Test
    public void execute_duplicateNewStudent_throwsCommandException() {
        Student studentInList = model.getStudentRecord().getStudentList().get(INDEX_TWO.getZeroBased());
        EditStudentDescriptor descriptor = new EditStudentDescriptor();
        descriptor.setName(studentInList.getName());
        StudentEditCommand editCommand = new StudentEditCommand(INDEX_ONE, descriptor);
        assertThrows(CommandException.class, () -> editCommand.execute(model), MESSAGE_DUPLICATE_STUDENT);
    }

    /**
     * Test for unsuccessfully editing a student due to index out of bounds.
     */
    @Test
    public void execute_invalidStudentIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditStudentDescriptor descriptor = new EditStudentDescriptor();
        descriptor.setName(new Name("Valid Name"));
        StudentEditCommand editCommand = new StudentEditCommand(outOfBoundIndex, descriptor);
        assertThrows(CommandException.class, () -> editCommand.execute(model), MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Tests if two StudentEditCommands are equal.
     */
    @Test
    public void equals() {
        final EditStudentDescriptor standardDescriptor = new StudentEditCommand.EditStudentDescriptor();
        standardDescriptor.setName(new Name("Valid Name"));
        final StudentEditCommand standardCommand = new StudentEditCommand(INDEX_ONE, standardDescriptor);

        final EditStudentDescriptor standardDescriptorCopy = new StudentEditCommand.EditStudentDescriptor();
        standardDescriptorCopy.setName(new Name("Valid Name"));
        StudentEditCommand standardCommandCopy = new StudentEditCommand(INDEX_ONE, standardDescriptorCopy);

        //same content -> returns true
        assertTrue(standardCommand.equals(standardCommandCopy));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new StudentListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new StudentEditCommand(INDEX_THREE, standardDescriptor)));

        // different descriptor -> returns false
        final EditStudentDescriptor differentDescriptor = new StudentEditCommand.EditStudentDescriptor();
        differentDescriptor.setName(new Name("Different Name"));
        assertFalse(standardCommand.equals(new StudentEditCommand(INDEX_ONE, differentDescriptor)));
    }

}

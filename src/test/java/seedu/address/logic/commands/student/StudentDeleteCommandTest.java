package seedu.address.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.student.StudentDeleteCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.student.TypicalStudents.getTypicalStudentRecord;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;

/**
 * Test for StudentDeleteCommand.
 */
public class StudentDeleteCommandTest {
    private Model model = new ModelManager();

    /**
     * Creates an instance of StudentDeleteCommandTest.
     */
    public StudentDeleteCommandTest() {
        model.setStudentRecord(getTypicalStudentRecord());
    }

    /**
     * Test for successfully deleting a student.
     */
    @Test
    public void execute_validIndex_success() throws Exception {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_ONE.getZeroBased());
        StudentDeleteCommand deleteCommand = new StudentDeleteCommand(INDEX_ONE);

        String expectedMessage = String.format(MESSAGE_SUCCESS, studentToDelete);

        CommandResult commandResult = deleteCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(commandResult.getCommandResultType(), CommandResultType.SHOW_STUDENT);
    }

    /**
     * Test for unsuccessfully deleting a student due to student index out of bounds.
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        StudentDeleteCommand deleteCommand = new StudentDeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model),
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Test if two StudentDeleteCommands are equal.
     */
    @Test
    public void equals() {
        StudentDeleteCommand deleteFirstCommand = new StudentDeleteCommand(INDEX_ONE);
        StudentDeleteCommand deleteSecondCommand = new StudentDeleteCommand(INDEX_TWO);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}

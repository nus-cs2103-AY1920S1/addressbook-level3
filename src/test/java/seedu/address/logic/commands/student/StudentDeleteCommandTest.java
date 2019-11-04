package seedu.address.logic.commands.student;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.student.TypicalStudents.getTypicalStudentRecord;

public class StudentDeleteCommandTest {
    private Model model = new ModelManager();

    public StudentDeleteCommandTest() {
        model.setStudentRecord(getTypicalStudentRecord());
    }

    @Test
    public void execute_validIndex_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_ONE.getZeroBased());
        StudentDeleteCommand deleteCommand = new StudentDeleteCommand(INDEX_ONE);

        String expectedMessage = String.format(StudentDeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        ModelManager expectedModel = new ModelManager();
        expectedModel.setStudentRecord(getTypicalStudentRecord());
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel,CommandResultType.SHOW_STUDENT);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        StudentDeleteCommand deleteCommand = new StudentDeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model), MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

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

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}

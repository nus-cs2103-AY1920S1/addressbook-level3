package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        ModelManager expectedModel = new ModelManager(model.getNotebook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        //assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }


    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_OBJECT);

        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        Model expectedModel = new ModelManager(model.getNotebook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        showNoStudent(expectedModel);

        //assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_OBJECT);

        Index outOfBoundIndex = INDEX_SECOND_OBJECT;
        // ensures that outOfBoundIndex is still in bounds of classroom list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getNotebook().getCurrentClassroom().getStudentList()
                .size());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(INDEX_FIRST_OBJECT);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(INDEX_SECOND_OBJECT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(INDEX_FIRST_OBJECT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}

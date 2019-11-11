package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_SECOND_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.student.Student;
import seedu.tarence.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(TypicalStudents.getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_IN_LIST.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS, studentToDelete);

        ModelManager expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model = new ModelManager(TypicalStudents.getTypicalApplication(), new UserPrefs());
        showStudentAtIndex(model, INDEX_FIRST_IN_LIST);
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_IN_LIST.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(INDEX_FIRST_IN_LIST);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS, studentToDelete);

        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        showNoStudent(expectedModel);
        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_IN_LIST);

        Index outOfBoundIndex = INDEX_SECOND_IN_LIST;
        // ensures that outOfBoundIndex is still in bounds of class list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getApplication().getStudentList().size());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(INDEX_FIRST_IN_LIST);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(INDEX_SECOND_IN_LIST);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(INDEX_FIRST_IN_LIST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show no students.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEarningsAtIndex;
import static seedu.address.testutil.TypicalEarnings.getTypicalTutorAid;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.earnings.Earnings;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteEarningsCommand}.
 */
public class DeleteEarningsCommandTest {


    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Earnings earningsToDelete = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        DeleteEarningsCommand deleteEarningsCommand = new DeleteEarningsCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEarningsCommand.MESSAGE_DELETE_EARNINGS_SUCCESS, earningsToDelete);

        ModelManager expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());
        expectedModel.deleteEarnings(earningsToDelete);
        expectedModel.commitTutorAid();
        assertCommandSuccess(deleteEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEarningsList().size() + 1);
        DeleteEarningsCommand deleteEarningsCommand = new DeleteEarningsCommand(outOfBoundIndex);

        assertCommandFailure(deleteEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEarningsAtIndex(model, INDEX_FIRST);

        Earnings earningsToDelete = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        DeleteEarningsCommand deleteEarningsCommand = new DeleteEarningsCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEarningsCommand.MESSAGE_DELETE_EARNINGS_SUCCESS, earningsToDelete);

        Model expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());
        expectedModel.deleteEarnings(earningsToDelete);
        showNoEarnings(expectedModel);
        expectedModel.commitTutorAid();
        assertCommandSuccess(deleteEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEarningsAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorAid().getEarningsList().size());

        DeleteEarningsCommand deleteEarningsCommand = new DeleteEarningsCommand(outOfBoundIndex);

        assertCommandFailure(deleteEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEarningsCommand deleteEarningsFirstCommand = new DeleteEarningsCommand(INDEX_FIRST);
        DeleteEarningsCommand deleteEarningsSecondCommand = new DeleteEarningsCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteEarningsFirstCommand.equals(deleteEarningsFirstCommand));

        // same values -> returns true
        DeleteEarningsCommand deleteEarningsFirstCommandCopy = new DeleteEarningsCommand(INDEX_FIRST);
        assertTrue(deleteEarningsFirstCommand.equals(deleteEarningsFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteEarningsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteEarningsFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteEarningsFirstCommand.equals(deleteEarningsSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEarnings(Model model) {
        model.updateFilteredEarningsList(p -> false);

        assertTrue(model.getFilteredEarningsList().isEmpty());
    }
}

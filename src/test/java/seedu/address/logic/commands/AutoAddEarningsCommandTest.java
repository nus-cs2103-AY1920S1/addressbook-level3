package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEarningsAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEarnings.getTypicalTutorAid;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.earnings.Count;
import seedu.address.model.earnings.Earnings;

public class AutoAddEarningsCommandTest {


    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void constructor_nullParamters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AutoAddEarningsCommand(null, null));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AutoAddEarningsCommand(null, new Count("0")));
    }

    @Test
    public void constructor_nullCount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AutoAddEarningsCommand(Index.fromZeroBased(0), null));
    }

    @Test
    public void execute_invalidIndexAndValidCountUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEarningsList().size() + 1);
        AutoAddEarningsCommand autoAddEarningsCommand = new AutoAddEarningsCommand(outOfBoundIndex, new Count("1"));

        assertCommandFailure(autoAddEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexAndValidCountFilteredList_success() {
        showEarningsAtIndex(model, INDEX_FIRST);

        Earnings earningsToAutomate = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        AutoAddEarningsCommand autoAddEarningsCommand = new AutoAddEarningsCommand(INDEX_FIRST, new Count("1"));

        String expectedMessage = String.format(AutoAddEarningsCommand.MESSAGE_SUCCESS, earningsToAutomate);

        Model expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());
        showNoEarnings(expectedModel);

        assertCommandSuccess(autoAddEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexAndValidCountFilteredList_throwsCommandException() {
        showEarningsAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorAid().getEarningsList().size());

        AutoAddEarningsCommand autoAddEarningsCommand = new AutoAddEarningsCommand(outOfBoundIndex, new Count("1"));

        assertCommandFailure(autoAddEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AutoAddEarningsCommand autoAddEarningsFirstCommand = new AutoAddEarningsCommand(INDEX_FIRST, new Count("1"));
        AutoAddEarningsCommand autoAddEarningsSecondCommand = new AutoAddEarningsCommand(INDEX_SECOND, new Count("1"));

        // same object -> returns true
        assertTrue(autoAddEarningsFirstCommand.equals(autoAddEarningsFirstCommand));

        // same values -> returns true
        AutoAddEarningsCommand autoAddEarningsFirstCommandCopy =
                new AutoAddEarningsCommand(INDEX_FIRST, new Count("1"));
        assertTrue(autoAddEarningsFirstCommand.equals(autoAddEarningsFirstCommandCopy));

        // different types -> returns false
        assertFalse(autoAddEarningsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(autoAddEarningsFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(autoAddEarningsFirstCommand.equals(autoAddEarningsSecondCommand));

        // different claim objects
        AutoAddEarningsCommand autoAddEarningsThirdCommand = new AutoAddEarningsCommand(INDEX_FIRST, new Count("4"));
        assertFalse(autoAddEarningsFirstCommand.equals(autoAddEarningsThirdCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEarnings(Model model) {
        model.updateFilteredEarningsList(p -> false);

        assertTrue(model.getFilteredEarningsList().isEmpty());
    }
}

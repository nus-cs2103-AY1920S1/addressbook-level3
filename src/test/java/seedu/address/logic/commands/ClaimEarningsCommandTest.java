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
import seedu.address.model.earnings.Claim;
import seedu.address.model.earnings.Earnings;

public class ClaimEarningsCommandTest {


    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void executeValidIndexAndClaimUnfilteredListWithApprovedStatusSuccess() {
        Earnings earningsToClaim = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        ClaimEarningsCommand claimEarningsCommand = new ClaimEarningsCommand(INDEX_FIRST, new Claim("approved"));

        String expectedMessage = String.format(ClaimEarningsCommand.MESSAGE_SUCCESS, earningsToClaim);

        ModelManager expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());

        assertCommandSuccess(claimEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexAndClaimUnfilteredListWithRejectedStatusSuccess() {
        Earnings earningsToClaim = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        ClaimEarningsCommand claimEarningsCommand = new ClaimEarningsCommand(INDEX_FIRST, new Claim("rejected"));

        String expectedMessage = String.format(ClaimEarningsCommand.MESSAGE_SUCCESS, earningsToClaim);

        ModelManager expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());

        assertCommandSuccess(claimEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexAndClaimUnfilteredListWithPendingSubmissionStatusSuccess() {
        Earnings earningsToClaim = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        ClaimEarningsCommand claimEarningsCommand =
                new ClaimEarningsCommand(INDEX_FIRST, new Claim("pending submission"));

        String expectedMessage = String.format(ClaimEarningsCommand.MESSAGE_SUCCESS, earningsToClaim);

        ModelManager expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());

        assertCommandSuccess(claimEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexAndClaimUnfilteredListWithProcessingStatusSuccess() {
        Earnings earningsToClaim = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        ClaimEarningsCommand claimEarningsCommand = new ClaimEarningsCommand(INDEX_FIRST, new Claim("processing"));

        String expectedMessage = String.format(ClaimEarningsCommand.MESSAGE_SUCCESS, earningsToClaim);

        ModelManager expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());

        assertCommandSuccess(claimEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexAndValidClaimUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEarningsList().size() + 1);
        ClaimEarningsCommand claimEarningsCommand = new ClaimEarningsCommand(outOfBoundIndex, new Claim("approved"));

        assertCommandFailure(claimEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexAndValidClaimFilteredList_success() {
        showEarningsAtIndex(model, INDEX_FIRST);

        Earnings earningsToClaim = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        ClaimEarningsCommand claimEarningsCommand = new ClaimEarningsCommand(INDEX_FIRST, new Claim("approved"));

        String expectedMessage = String.format(ClaimEarningsCommand.MESSAGE_SUCCESS, earningsToClaim);

        Model expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());
        showNoEarnings(expectedModel);

        assertCommandSuccess(claimEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexAndValidClaimFilteredList_throwsCommandException() {
        showEarningsAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorAid().getEarningsList().size());

        ClaimEarningsCommand claimEarningsCommand = new ClaimEarningsCommand(outOfBoundIndex, new Claim("rejected"));

        assertCommandFailure(claimEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ClaimEarningsCommand claimEarningsFirstCommand = new ClaimEarningsCommand(INDEX_FIRST, new Claim("approved"));
        ClaimEarningsCommand claimEarningsSecondCommand = new ClaimEarningsCommand(INDEX_SECOND, new Claim("approved"));

        // same object -> returns true
        assertTrue(claimEarningsFirstCommand.equals(claimEarningsFirstCommand));

        // same values -> returns true
        ClaimEarningsCommand claimEarningsFirstCommandCopy =
                new ClaimEarningsCommand(INDEX_FIRST, new Claim("approved"));
        assertTrue(claimEarningsFirstCommand.equals(claimEarningsFirstCommandCopy));

        // different types -> returns false
        assertFalse(claimEarningsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(claimEarningsFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(claimEarningsFirstCommand.equals(claimEarningsSecondCommand));

        // different claim objects
        ClaimEarningsCommand claimEarningsThirdCommand = new ClaimEarningsCommand(INDEX_FIRST, new Claim("rejected"));
        assertFalse(claimEarningsFirstCommand.equals(claimEarningsThirdCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEarnings(Model model) {
        model.updateFilteredEarningsList(p -> false);

        assertTrue(model.getFilteredEarningsList().isEmpty());
    }
}

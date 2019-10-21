package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalFinSec;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;

class CheckCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Claim claimToCheck = model.getFilteredClaimList().get(INDEX_FIRST_PERSON.getZeroBased());
        CheckCommand checkCommand = new CheckCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_PERSON_SUCCESS, checkCommand);

        ModelManager expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
        expectedModel.handleClaim(claimToCheck);

        assertCommandSuccess(checkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClaimList().size() + 1);
        CheckCommand checkCommand = new CheckCommand(outOfBoundIndex);

        assertCommandFailure(checkCommand, model, Messages.MESSAGE_INVALID_CLAIM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CheckCommand checkFirstCommand = new CheckCommand(INDEX_FIRST_PERSON);
        CheckCommand checkSecondCommand = new CheckCommand(INDEX_SECOND_PERSON);

        // same object -> true
        assertTrue(checkFirstCommand.equals(checkFirstCommand));

        // same values -> true
        CheckCommand checkFirstCommandCopy = new CheckCommand(INDEX_FIRST_PERSON);
        assertTrue(checkFirstCommand.equals(checkFirstCommandCopy));

        // different types -> false
        assertFalse(checkFirstCommand.equals(1));

        // null -> false
        assertFalse(checkFirstCommand.equals(null));

        //different index -> false
        assertFalse(checkFirstCommand.equals(checkSecondCommand));
    }
}

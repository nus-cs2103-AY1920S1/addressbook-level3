package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author {lawncegoh}
class CheckCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(null));
    }

    //    @Test
    //    public void execute_validIndexUnfilteredList_success() {
    //        Claim claimToCheck = model.getFilteredClaimList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        CheckCommand checkCommand = new CheckCommand(INDEX_FIRST_PERSON);
    //
    //        String expectedMessage = String.format(CheckCommand.MESSAGE_SUCCESS_CLAIM, checkCommand);
    //
    //        ModelManager expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
    //        Model.handleClaim(claimToCheck);
    //
    //        assertCommandSuccess(checkCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
    //        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClaimList().size() + 1);
    //        CheckCommand checkCommand = new CheckCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(checkCommand, model, Messages.MESSAGE_INVALID_CLAIM_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        CheckCommand checkFirstCommand = new CheckCommand(INDEX_FIRST_PERSON);
        CheckCommand checkSecondCommand = new CheckCommand(INDEX_SECOND_PERSON);

        // same object -> true
        assertTrue(checkFirstCommand.equals(checkFirstCommand));

        // different types -> false
        assertFalse(checkFirstCommand.equals(1));

        // null -> false
        assertFalse(checkFirstCommand.equals(null));

        //different index -> false
        assertFalse(checkFirstCommand.equals(checkSecondCommand));
    }
}

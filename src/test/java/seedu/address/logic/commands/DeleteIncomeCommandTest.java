package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.income.Income;

class DeleteIncomeCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Income incomeToDelete = model.getFilteredIncomeList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteIncomeCommand.MESSAGE_DELETE_INCOME_SUCCESS, incomeToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
        expectedModel.deleteIncome(incomeToDelete);

        assertCommandSuccess(deleteIncomeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteIncomeCommand deleteIncomeCommand = new DeleteIncomeCommand(outOfBoundIndex);

        assertCommandFailure(deleteIncomeCommand, model, Messages.MESSAGE_INVALID_INCOME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteIncomeCommand deleteFirstIncomeCommand = new DeleteIncomeCommand(INDEX_FIRST_PERSON);
        DeleteIncomeCommand deleteSecondIncomeCommand = new DeleteIncomeCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstIncomeCommand.equals(deleteFirstIncomeCommand));

        // same values -> returns true
        DeleteIncomeCommand deleteFirstIncomeCommandCopy = new DeleteIncomeCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstIncomeCommand.equals(deleteFirstIncomeCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstIncomeCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstIncomeCommand.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstIncomeCommand.equals(deleteSecondIncomeCommand));
    }

}

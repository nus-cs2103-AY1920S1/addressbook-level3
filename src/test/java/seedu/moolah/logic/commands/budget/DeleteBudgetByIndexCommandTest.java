package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;

public class DeleteBudgetByIndexCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_validIndexUnfilteredList_success() {
        Budget budgetToDelete = model.getFilteredBudgetList().get(INDEX_SECOND.getZeroBased());
        DeleteBudgetByIndexCommand command = new DeleteBudgetByIndexCommand(INDEX_SECOND);

        String expectedMessage = String.format(DeleteBudgetByIndexCommand.MESSAGE_DELETE_BUDGET_SUCCESS,
                budgetToDelete);

        expectedModel.deleteBudget(budgetToDelete);
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBudgetList().size() + 1);
        DeleteBudgetByIndexCommand deleteBudgetByIndexCommand = new DeleteBudgetByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteBudgetByIndexCommand, model, Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
    }

    @Test
    public void run_onlyDefaultBudgetLeft_throwsCommandException() {
        Model emptyModel = new ModelManager(new MooLah(), new UserPrefs(), new ModelHistory());
        DeleteBudgetByIndexCommand deleteBudgetByIndexCommand = new DeleteBudgetByIndexCommand(INDEX_FIRST);

        assertCommandFailure(deleteBudgetByIndexCommand, emptyModel, Messages.MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET);
    }

    @Test
    public void run_tryingToDeleteDefaultBudget_throwsCommandException() {
        DeleteBudgetByIndexCommand deleteBudgetByIndexCommand = new DeleteBudgetByIndexCommand(INDEX_FIRST);

        assertCommandFailure(deleteBudgetByIndexCommand, model, Messages.MESSAGE_CANNOT_DELETE_DEFAULT_BUDGET);
    }


    @Test
    public void equals() {
        DeleteBudgetByIndexCommand deleteFirstCommand = new DeleteBudgetByIndexCommand(INDEX_FIRST);
        DeleteBudgetByIndexCommand deleteSecondCommand = new DeleteBudgetByIndexCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteBudgetByIndexCommand deleteFirstCommandCopy = new DeleteBudgetByIndexCommand(INDEX_FIRST);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different expense -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }
}


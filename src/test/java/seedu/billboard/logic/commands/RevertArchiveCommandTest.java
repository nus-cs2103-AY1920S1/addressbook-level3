package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboardWithArchiveExpenses;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.expense.Expense;

public class RevertArchiveCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboardWithArchiveExpenses(), new UserPrefs());
    }

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RevertArchiveCommand(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void constructor_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RevertArchiveCommand(null, INDEX_FIRST_EXPENSE));
    }

    @Test
    public void constructor_nullArchiveNameAndExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RevertArchiveCommand(null, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> new RevertArchiveCommand(VALID_ARCHIVE_TAXES,
                INDEX_FIRST_EXPENSE).execute(null));
    }

    @Test
    public void execute_validExpenseAndIndex_success() {
        Model expectedModel = new ModelManager(new Billboard(model.getCombinedBillboard()), new UserPrefs());
        Expense expenseToUnarchive = expectedModel.getFilteredArchiveExpenses("luxury")
                .get(INDEX_FIRST_EXPENSE.getZeroBased());
        expectedModel.deleteArchiveExpense("luxury", expenseToUnarchive);
        expectedModel.addExpense(expenseToUnarchive);
        RevertArchiveCommand revertArchiveCommand = new RevertArchiveCommand("luxury", INDEX_FIRST_EXPENSE);
        String expectedMessage = String.format(RevertArchiveCommand.MESSAGE_SUCCESS,
                expenseToUnarchive.getName(), "luxury");

        assertCommandSuccess(revertArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_revertLastExpenseInArchive_success() {
        // So that the "luxury" archive in model will have only one expense
        Expense intialRemoveExpense = model.getFilteredArchiveExpenses("luxury")
                .get(INDEX_FIRST_EXPENSE.getZeroBased());
        model.deleteArchiveExpense("luxury", intialRemoveExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getCombinedBillboard()), new UserPrefs());
        Expense expenseToUnarchive = expectedModel.getFilteredArchiveExpenses("luxury")
                .get(INDEX_FIRST_EXPENSE.getZeroBased());
        expectedModel.deleteArchiveExpense("luxury", expenseToUnarchive);
        expectedModel.deleteArchive("luxury");
        expectedModel.addExpense(expenseToUnarchive);

        RevertArchiveCommand revertArchiveCommand = new RevertArchiveCommand("luxury", INDEX_FIRST_EXPENSE);
        String expectedMessage = String.format(RevertArchiveCommand.MESSAGE_SUCCESS,
                expenseToUnarchive.getName(), "luxury");
        expectedMessage += "\n" + String.format(RevertArchiveCommand.MESSAGE_EMPTY_ARCHIVE_AFTER_REVERT_EXPENSE,
                "luxury");

        assertCommandSuccess(revertArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        List<Expense> archiveList = model.getFilteredArchiveExpenses("luxury");
        Index outOfBoundIndex = Index.fromOneBased(archiveList.size() + 1);
        RevertArchiveCommand revertArchiveCommand = new RevertArchiveCommand("luxury", outOfBoundIndex);

        assertCommandFailure(revertArchiveCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonExistentArchive_failure() {
        RevertArchiveCommand revertArchiveCommand = new RevertArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        assertCommandFailure(revertArchiveCommand, model, Messages.MESSAGE_NONEXISTENT_ARCHIVE_ENTERED);
    }

    @Test
    public void equals() {
        final RevertArchiveCommand standardCommand = new RevertArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);

        // same values -> returns true
        RevertArchiveCommand commandWithSameValues = new RevertArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RevertArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_SECOND_EXPENSE)));

        // different tag names -> returns false
        assertFalse(standardCommand.equals(new RevertArchiveCommand("luxury", INDEX_FIRST_EXPENSE)));
    }
}

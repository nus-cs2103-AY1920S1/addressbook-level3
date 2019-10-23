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

public class DeleteArchiveCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboardWithArchiveExpenses(), new UserPrefs());
    }

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteArchiveCommand(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void constructor_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteArchiveCommand(null, INDEX_FIRST_EXPENSE));
    }

    @Test
    public void constructor_nullArchiveNameAndExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteArchiveCommand(null, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> new DeleteArchiveCommand(VALID_ARCHIVE_TAXES,
                INDEX_FIRST_EXPENSE).execute(null));
    }

    @Test
    public void execute_validExpenseAndIndex_success() {
        Model expectedModel = new ModelManager(new Billboard(model.getCombinedBillboard()), new UserPrefs());
        Expense expenseToDelete = expectedModel.getFilteredArchiveExpenses("luxury")
                .get(INDEX_FIRST_EXPENSE.getZeroBased());
        expectedModel.deleteArchiveExpense("luxury", expenseToDelete);
        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand("luxury", INDEX_FIRST_EXPENSE);
        String expectedMessage = String.format(DeleteArchiveCommand.MESSAGE_DELETE_EXPENSE_SUCCESS,
                expenseToDelete.getName(), "luxury");

        assertCommandSuccess(deleteArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteLastExpenseInArchive_success() {
        // So that the "luxury" archive in model will have only one expense
        Expense intialRemoveExpense = model.getFilteredArchiveExpenses("luxury")
                .get(INDEX_FIRST_EXPENSE.getZeroBased());
        model.deleteArchiveExpense("luxury", intialRemoveExpense);

        Model expectedModel = new ModelManager(new Billboard(model.getCombinedBillboard()), new UserPrefs());
        Expense expenseToDelete = expectedModel.getFilteredArchiveExpenses("luxury")
                .get(INDEX_FIRST_EXPENSE.getZeroBased());
        expectedModel.deleteArchiveExpense("luxury", expenseToDelete);
        expectedModel.deleteArchive("luxury");
        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand("luxury", INDEX_FIRST_EXPENSE);
        String expectedMessage = String.format(DeleteArchiveCommand.MESSAGE_DELETE_EXPENSE_SUCCESS,
                expenseToDelete.getName(), "luxury");
        expectedMessage += "\n" + String.format(DeleteArchiveCommand.MESSAGE_EMPTY_ARCHIVE_AFTER_DELETE_EXPENSE,
                "luxury");

        assertCommandSuccess(deleteArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        List<Expense> archiveList = model.getFilteredArchiveExpenses("luxury");
        Index outOfBoundIndex = Index.fromOneBased(archiveList.size() + 1);
        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand("luxury", outOfBoundIndex);

        assertCommandFailure(deleteArchiveCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonExistentArchive_failure() {
        DeleteArchiveCommand deleteArchiveCommand = new DeleteArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        assertCommandFailure(deleteArchiveCommand, model, Messages.MESSAGE_NONEXISTENT_ARCHIVE_ENTERED);
    }

    @Test
    public void equals() {
        final DeleteArchiveCommand standardCommand = new DeleteArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);

        // same values -> returns true
        DeleteArchiveCommand commandWithSameValues = new DeleteArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_SECOND_EXPENSE)));

        // different tag names -> returns false
        assertFalse(standardCommand.equals(new DeleteArchiveCommand("luxury", INDEX_FIRST_EXPENSE)));
    }
}

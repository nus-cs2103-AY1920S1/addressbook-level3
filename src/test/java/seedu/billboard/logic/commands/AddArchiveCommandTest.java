package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboardWithArchiveExpenses;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddArchiveCommand}.
 */
public class AddArchiveCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBillboardWithArchiveExpenses(), new UserPrefs());
    }

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddArchiveCommand(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void constructor_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddArchiveCommand(null, INDEX_FIRST_EXPENSE));
    }

    @Test
    public void constructor_nullArchiveNameAndExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddArchiveCommand(null, null));
    }

    @Test
    public void execute_validIndexNonExistentArchiveUnfilteredList_success() {
        Expense expenseToArchive = model.getFilteredExpenses().get(INDEX_FIRST_EXPENSE.getZeroBased());
        AddArchiveCommand addArchiveCommand = new AddArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(AddArchiveCommand.MESSAGE_SUCCESS_CREATE_ARCHIVE,
                VALID_ARCHIVE_TAXES, expenseToArchive.getName(), VALID_ARCHIVE_TAXES);

        ModelManager expectedModel = new ModelManager(model.getCombinedBillboard(), new UserPrefs());
        expectedModel.deleteExpense(expenseToArchive);
        expenseToArchive.archiveTo(VALID_ARCHIVE_TAXES);
        if (!expectedModel.hasArchive(VALID_ARCHIVE_TAXES)) {
            expectedModel.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        }
        expectedModel.addArchiveExpense(VALID_ARCHIVE_TAXES, expenseToArchive);

        assertCommandSuccess(addArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexExistingArchiveUnfilteredList_success() {
        Expense expenseToArchive = model.getFilteredExpenses().get(INDEX_FIRST_EXPENSE.getZeroBased());
        AddArchiveCommand addArchiveCommand = new AddArchiveCommand("luxury", INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(AddArchiveCommand.MESSAGE_SUCCESS_EXISTING_ARCHIVE,
                expenseToArchive.getName(), "luxury");

        ModelManager expectedModel = new ModelManager(model.getCombinedBillboard(), new UserPrefs());
        expectedModel.deleteExpense(expenseToArchive);
        expenseToArchive.archiveTo("luxury");
        expectedModel.addArchiveExpense("luxury", expenseToArchive);

        assertCommandSuccess(addArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenses().size() + 1);
        AddArchiveCommand addArchiveCommand = new AddArchiveCommand(VALID_ARCHIVE_TAXES, outOfBoundIndex);

        assertCommandFailure(addArchiveCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddArchiveCommand firstAddArchiveCommand = new AddArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        AddArchiveCommand secondAddArchiveCommand = new AddArchiveCommand(VALID_ARCHIVE_DINNER, INDEX_SECOND_EXPENSE);

        // same object -> returns true
        assertEquals(firstAddArchiveCommand, firstAddArchiveCommand);

        // same values -> returns true
        AddArchiveCommand firstAddArchiveCommandCopy = new AddArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        assertEquals(firstAddArchiveCommand, firstAddArchiveCommandCopy);

        // different types -> returns false
        assertNotEquals(1, firstAddArchiveCommand);

        // null -> returns false
        assertNotEquals(null, firstAddArchiveCommand);

        // different expense -> returns false
        assertNotEquals(firstAddArchiveCommand, secondAddArchiveCommand);
    }
}

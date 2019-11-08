package seedu.ichifund.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_ANIME;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_ALLOWANCE;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_BUS;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.testutil.FundBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FundBook(), new FundBook(modelManager.getFundBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFundBookFilePath(Paths.get("fund/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFundBookFilePath(Paths.get("new/fund/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setFundBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setFundBookFilePath(null));
    }

    @Test
    public void setFundBookFilePath_validPath_setsFundBookFilePath() {
        Path path = Paths.get("fund/book/file/path");
        modelManager.setFundBookFilePath(path);
        assertEquals(path, modelManager.getFundBookFilePath());
    }

    @Test
    public void hasBudget_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasBudget(null));
    }

    @Test
    public void hasBudget_budgetNotInFundBook_returnsFalse() {
        assertFalse(modelManager.hasBudget(BUDGET_ANIME));
    }

    @Test
    public void hasBudget_budgetInFundBook_returnsTrue() {
        modelManager.addBudget(BUDGET_ANIME);
        assertTrue(modelManager.hasBudget(BUDGET_ANIME));
    }

    @Test
    public void getFilteredBudgetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBudgetList().remove(0));
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTransactionList().remove(0));
    }

    @Test
    public void equals() {
        FundBook fundBook = new FundBookBuilder().withTransaction(TRANSACTION_ALLOWANCE)
                .withTransaction(TRANSACTION_BUS).build();
        FundBook differentFundBook = new FundBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(fundBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(fundBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different fundBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFundBook, userPrefs)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFundBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(fundBook, differentUserPrefs)));
    }
}

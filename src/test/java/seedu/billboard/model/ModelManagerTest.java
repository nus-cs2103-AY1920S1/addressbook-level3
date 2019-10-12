package seedu.billboard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.FOOD;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.model.expense.NameContainsKeywordsPredicate;
import seedu.billboard.testutil.BillboardBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Billboard(), new Billboard(modelManager.getBillboardExpenses()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setBillboardFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setBillboardFilePath(Paths.get("new/address/book/file/path"));
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
    public void setBillboardFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setBillboardFilePath(null));
    }

    @Test
    public void setBillboardFilePath_validPath_setsBillboardFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setBillboardFilePath(path);
        assertEquals(path, modelManager.getBillboardFilePath());
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasExpense(null));
    }

    @Test
    public void hasExpense_expenseNotInBillboard_returnsFalse() {
        assertFalse(modelManager.hasExpense(BILLS));
    }

    @Test
    public void hasExpense_expenseInBillboard_returnsTrue() {
        modelManager.addExpense(BILLS);
        assertTrue(modelManager.hasExpense(BILLS));
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredExpenses().remove(0));
    }

    @Test
    public void equals() {
        Billboard billboard = new BillboardBuilder().withExpense(BILLS).withExpense(FOOD).build();
        Billboard differentBillboard = new Billboard();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(billboard, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(billboard, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different billboard -> returns false
        assertNotEquals(modelManager, new ModelManager(differentBillboard, userPrefs));

        // different filteredList -> returns false
        String[] keywords = BILLS.getName().name.split("\\s+");
        modelManager.updateFilteredExpenses(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(billboard, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setBillboardFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(billboard, differentUserPrefs));
    }
}

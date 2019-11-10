package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalBudgets.getTypicalBudgetList;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.budget.ReadOnlyBudgetList;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonExpenseListStorage expenseListStorage = new JsonExpenseListStorage(getTempFilePath("ab"));
        JsonBudgetListStorage budgetListStorage = new JsonBudgetListStorage(getTempFilePath("bl"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonExchangeDataStorage exchangeDataStorage = new JsonExchangeDataStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(expenseListStorage, budgetListStorage,
            exchangeDataStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void expenseListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonExpenseListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonExpenseListStorageTest} class.
         */
        ExpenseList original = getTypicalExpenseList();
        storageManager.saveExpenseList(original);
        ReadOnlyExpenseList retrieved = storageManager.readExpenseList().get();
        assertEquals(original, new ExpenseList(retrieved));
    }

    @Test
    public void getExpenseListFilePath() {
        assertNotNull(storageManager.getExpenseListFilePath());
    }

    @Test
    public void getExchangeDataFilePath() {
        assertNotNull(storageManager.getExchangeDataFilePath());
    }

    @Test
    public void budgetListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonBudgetListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonBudgetListStorageTest} class.
         */
        BudgetList original = getTypicalBudgetList();
        storageManager.saveBudgetList(original);
        ReadOnlyBudgetList retrieved = storageManager.readBudgetList().get();
        assertEquals(original, new BudgetList(retrieved));
    }

    @Test
    public void getBudgetListFilePath() {
        assertNotNull(storageManager.getBudgetListFilePath());
    }
}

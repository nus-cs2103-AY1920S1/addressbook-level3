package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TestUtil.makeModelStack;
import static seedu.address.testutil.TypicalExpenses.ANNIVERSARY;
import static seedu.address.testutil.TypicalExpenses.BUSAN_TRIP;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.address.testutil.MooLahBuilder;

public class ModelManagerTest {

    private ModelManager modelManager;
    private ModelManager expectedModelManager;

    @BeforeEach
    public void setup() {
        modelManager = new ModelManager();
        expectedModelManager = new ModelManager(modelManager);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new MooLah(), new MooLah(modelManager.getMooLah()));
        assertEquals(new ModelHistory(), modelManager.getModelHistory());
    }

    @Test
    public void resetData_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.resetData(null));
    }

    @Test
    public void resetData_success() {
        Model other = new ModelManager(modelManager.getMooLah(), modelManager.getUserPrefs(), new ModelHistory());

        modelManager.resetData(other);
        assertEquals(modelManager, other);
    }

    @Test
    public void rollbackModel_noModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.rollbackModel());
    }

    @Test
    public void rollbackModel_hasModel_returnsModelOptional() {
        Model other = new ModelManager();
        modelManager.addToPastHistory(other);
        expectedModelManager.addToFutureHistory(other);

        modelManager.rollbackModel();
        assertEquals(modelManager.getModelHistory(), expectedModelManager.getModelHistory());
    }

    @Test
    public void migrateModel_noModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.migrateModel());

    }

    @Test
    public void migrateModel_hasModel_returnsModelOptional() {
        Model other = new ModelManager();
        modelManager.addToFutureHistory(other);
        expectedModelManager.addToPastHistory(other);

        modelManager.migrateModel();
        assertEquals(modelManager.getModelHistory(), expectedModelManager.getModelHistory());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        //userPrefs.setMooLahFilePath(Paths.get("address/book/file/path"));
        userPrefs.setMooLahFilePath(Paths.get("moolah/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        //userPrefs.setMooLahFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setMooLahFilePath(Paths.get("new/moolah/file/path"));
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
    public void setMooLahFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setMooLahFilePath(null));
    }

    @Test
    public void setMooLahFilePath_validPath_setsMooLahFilePath() {
        //Path path = Paths.get("address/book/file/path");
        Path path = Paths.get("moolah/file/path");
        modelManager.setMooLahFilePath(path);
        assertEquals(path, modelManager.getMooLahFilePath());
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasExpense(null));
    }

    @Test
    public void hasExpense_expenseNotInMooLah_returnsFalse() {
        assertFalse(modelManager.hasExpense(ANNIVERSARY));
    }

    @Test
    public void hasExpense_expenseInMooLah_returnsTrue() {
        modelManager.addExpense(ANNIVERSARY);
        assertTrue(modelManager.hasExpense(ANNIVERSARY));
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredExpenseList().remove(0));
    }

    @Test
    public void equals() {
        MooLah mooLah = new MooLahBuilder().withExpense(ANNIVERSARY).withExpense(BUSAN_TRIP).build();
        MooLah differentMooLah = new MooLah();
        UserPrefs userPrefs = new UserPrefs();
        ModelHistory modelHistory = new ModelHistory();

        // same values -> returns true
        modelManager = new ModelManager(mooLah, userPrefs, modelHistory);
        ModelManager modelManagerCopy = new ModelManager(mooLah, userPrefs, modelHistory);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different mooLah -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMooLah, userPrefs, modelHistory)));

        // different filteredList -> returns false
        String[] keywords = ANNIVERSARY.getDescription().fullDescription.split("\\s+");
        modelManager.updateFilteredExpenseList(new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(mooLah, userPrefs, modelHistory)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMooLahFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(mooLah, differentUserPrefs, modelHistory)));

        // different history -> returns false
        ModelHistory differentHistory = new ModelHistory("", makeModelStack(modelManager), makeModelStack());
        modelManagerCopy.setModelHistory(differentHistory);
        assertFalse(modelManager.equals(modelManagerCopy));
    }
}

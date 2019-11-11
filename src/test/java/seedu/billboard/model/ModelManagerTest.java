package seedu.billboard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.FOOD;
import static seedu.billboard.testutil.TypicalExpenses.FOOTBALL;
import static seedu.billboard.testutil.TypicalExpenses.GUCCI_SLIDES;
import static seedu.billboard.testutil.TypicalExpenses.IPHONE11;
import static seedu.billboard.testutil.TypicalExpenses.KPOP_LIGHT_STICK;
import static seedu.billboard.testutil.TypicalExpenses.TAXES;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.MultiArgPredicate;
import seedu.billboard.model.expense.exceptions.ExpenseNotFoundException;
import seedu.billboard.testutil.BillboardBuilder;

public class ModelManagerTest {

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Billboard(), new Billboard(modelManager.getBillboard()));
    }

    // user prefs/gui settings tests ===============================================
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

    // Billboard tests ===============================================
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

    // ArchiveWrapper tests ===============================================
    @Test
    public void getArchiveNames_noArchiveNames_success() {
        assertEquals(new ArrayList<>(), modelManager.getArchiveNames());
    }

    @Test
    public void getArchiveNames_nonEmptyArchiveNames_success() {
        modelManager.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        List<String> expectedArchiveNameList = new ArrayList<>();
        expectedArchiveNameList.add(VALID_ARCHIVE_TAXES);
        assertEquals(expectedArchiveNameList, modelManager.getArchiveNames());
    }

    @Test
    public void setArchives_nullArchiveWrapper_nullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setArchives(null));
    }

    @Test
    public void getArchives_noArchives_success() {
        assertEquals(new ArchiveWrapper(new ArrayList<>()), modelManager.getArchives());
    }

    @Test
    public void hasArchiveExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasArchiveExpense(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void hasArchiveExpense_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasArchiveExpense(null, TAXES));
    }

    @Test
    public void hasArchiveExpense_nonExistentArchive_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasArchiveExpense(VALID_ARCHIVE_TAXES, TAXES));
    }

    @Test
    public void hasArchiveExpense_expenseNotInArchive_returnsFalse() {
        modelManager.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        assertFalse(modelManager.hasArchiveExpense(VALID_ARCHIVE_TAXES, TAXES));
    }

    @Test
    public void hasArchiveExpense_expenseInArchive_returnsTrue() {
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(TAXES);
        modelManager.addArchive(new Archive(VALID_ARCHIVE_TAXES, expenseList));
        assertTrue(modelManager.hasArchiveExpense(VALID_ARCHIVE_TAXES, TAXES));
    }

    @Test
    public void hasArchive_nullArchive_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasArchive(null));
    }

    @Test
    public void deleteArchive_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteArchive(null));
    }

    @Test
    public void deleteArchive_existingArchiveName_success() {
        modelManager.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        modelManager.deleteArchive(VALID_ARCHIVE_TAXES);
        ModelManager expectedModelManager = new ModelManager();

        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void deleteArchive_nonExistentArchiveName_success() {
        modelManager.deleteArchive(VALID_ARCHIVE_TAXES);
        ModelManager expectedModelManager = new ModelManager();
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void deleteArchiveExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteArchiveExpense(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void deleteArchiveExpense_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteArchiveExpense(null, TAXES));
    }

    @Test
    public void deleteArchiveExpense_validArchiveNameAndExpense_success() {
        modelManager.addArchive(new Archive("hobbies", new ArrayList<>(Arrays.asList(KPOP_LIGHT_STICK, FOOTBALL))));
        modelManager.deleteArchiveExpense("hobbies", KPOP_LIGHT_STICK);

        ModelManager expectedModelManager = new ModelManager();
        expectedModelManager.addArchive(new Archive("hobbies", new ArrayList<>(Arrays.asList(FOOTBALL))));

        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void deleteArchiveExpense_nonExistentArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager
                .deleteArchiveExpense(VALID_ARCHIVE_TAXES, KPOP_LIGHT_STICK));
    }

    @Test
    public void deleteArchiveExpense_nonExistentExpenseInExistingArchive_throwsExpenseNotFoundException() {
        modelManager.addArchive(new Archive("hobbies", new ArrayList<>(Arrays.asList(KPOP_LIGHT_STICK, FOOTBALL))));
        assertThrows(ExpenseNotFoundException.class, () -> modelManager.deleteArchiveExpense("hobbies", GUCCI_SLIDES));
    }

    @Test
    public void addArchive_nullArchive_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addArchive(null));
    }

    @Test
    public void addArchive_validArchive_success() {
        modelManager.addArchive(new Archive("hobbies", new ArrayList<>(Arrays.asList(KPOP_LIGHT_STICK, FOOTBALL))));

        Billboard expectedBillboard = new Billboard();
        expectedBillboard.setExpenses(new ArrayList<>(Arrays.asList(KPOP_LIGHT_STICK, FOOTBALL)));
        ModelManager expectedModelManager = new ModelManager(expectedBillboard, new UserPrefs());

        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void addArchiveExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addArchiveExpense(VALID_ARCHIVE_TAXES, null));
    }

    @Test
    public void addArchiveExpense_nullArchiveName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addArchiveExpense(null, TAXES));
    }

    @Test
    public void getFilteredArchiveExpenses_modifyList_throwsUnsupportedOperationException() {
        modelManager.addArchive(new Archive(VALID_ARCHIVE_TAXES, new ArrayList<>()));
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredArchiveExpenses
                (VALID_ARCHIVE_TAXES).remove(0));
    }

    @Test
    public void getCombinedBillboard_validBillboardArchiveWrapper_success() {
        Billboard billboard = new Billboard();
        assertEquals(billboard, modelManager.getCombinedBillboard());
    }

    // Other tests ===============================================
    @Test
    public void equals() {
        Billboard billboard = new BillboardBuilder().withExpense(BILLS).withExpense(FOOD)
                .withExpense(IPHONE11).withExpense(KPOP_LIGHT_STICK).build();
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
        MultiArgPredicate predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList(keywords));
        modelManager.updateFilteredExpenses(predicate);
        assertNotEquals(modelManager, new ModelManager(billboard, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setBillboardFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(billboard, differentUserPrefs));
    }
}

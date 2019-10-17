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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

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
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new ModelHistory(), modelManager.getModelHistory());
    }

    @Test
    public void resetData_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.resetData(null));
    }

    @Test
    public void resetData_success() {
        Model other = new ModelManager(modelManager.getAddressBook(), modelManager.getUserPrefs(), new ModelHistory());

        modelManager.resetData(other);
        assertEquals(modelManager, other);
    }

    @Test
    public void rollbackModel_noModel_returnsEmptyOptional() {
        ModelHistory before = new ModelHistory(modelManager.getModelHistory());
        Optional<Model> model = modelManager.rollbackModel();
        assertEquals(before, modelManager.getModelHistory());
        assertTrue(model.isEmpty());
    }

    @Test
    public void rollbackModel_hasModel_returnsModelOptional() {
        Model other = new ModelManager();
        modelManager.addToPastHistory(other);
        expectedModelManager.addToFutureHistory(other);

        Optional<Model> model = modelManager.rollbackModel();
        assertTrue(model.isPresent());
        assertEquals(model.get().getModelHistory(), expectedModelManager.getModelHistory());
    }

    @Test
    public void migrateModel_noModel_returnsEmptyOptional() {
        ModelHistory before = new ModelHistory(modelManager.getModelHistory());
        Optional<Model> model = modelManager.migrateModel();
        assertEquals(before, modelManager.getModelHistory());
        assertTrue(model.isEmpty());
    }

    @Test
    public void migrateModel_hasModel_returnsModelOptional() {
        Model other = new ModelManager();
        modelManager.addToFutureHistory(other);
        expectedModelManager.addToPastHistory(other);

        Optional<Model> model = modelManager.migrateModel();
        assertTrue(model.isPresent());
        assertEquals(model.get().getModelHistory(), expectedModelManager.getModelHistory());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
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
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasExpense(null));
    }

    @Test
    public void hasExpense_expenseNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasExpense(ANNIVERSARY));
    }

    @Test
    public void hasExpense_expenseInAddressBook_returnsTrue() {
        modelManager.addExpense(ANNIVERSARY);
        assertTrue(modelManager.hasExpense(ANNIVERSARY));
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredExpenseList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withExpense(ANNIVERSARY).withExpense(BUSAN_TRIP).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelHistory modelHistory = new ModelHistory();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, modelHistory);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, modelHistory);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, modelHistory)));

        // different filteredList -> returns false
        String[] keywords = ANNIVERSARY.getDescription().fullDescription.split("\\s+");
        modelManager.updateFilteredExpenseList(new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, modelHistory)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, modelHistory)));

        // different history -> returns false
        ModelHistory differentHistory = new ModelHistory(makeModelStack(modelManager), makeModelStack());
        modelManagerCopy.setModelHistory(differentHistory);
        assertFalse(modelManager.equals(modelManagerCopy));
    }
}

package seedu.moolah.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TestUtil.makeModelChangesStack;
import static seedu.moolah.testutil.TypicalMooLah.ANNIVERSARY;
import static seedu.moolah.testutil.TypicalMooLah.BUSAN_TRIP;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.testutil.MooLahBuilder;

public class ModelManagerTest {

    private ModelManager modelManager;
    private ModelManager expectedModelManager;

    @BeforeEach
    public void setup() {
        modelManager = new ModelManager();
        expectedModelManager = new ModelManager();
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
    public void applyChanges_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.applyChanges(null));
    }

    @Test
    public void applyChanges_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMooLahFilePath(Paths.get("dummyFilepath"));

        ModelChanges changes = new ModelChanges("dummy")
                .setMooLah(getTypicalMooLah())
                .setUserPrefs(userPrefs)
                .setExpensePredicate(expense -> false)
                .setEventPredicate(event -> false)
                .setBudgetPredicate(budget -> false);

        expectedModelManager.setMooLah(getTypicalMooLah());
        expectedModelManager.setUserPrefs(userPrefs);
        expectedModelManager.updateFilteredExpenseList(expense -> false);
        expectedModelManager.updateFilteredEventList(event -> false);
        expectedModelManager.updateFilteredBudgetList(budget -> false);

        modelManager.applyChanges(changes);
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void commit_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.commit(null, null));
    }

    @Test
    public void commit_success() {
        ModelChanges past = new ModelChanges("past");
        ModelChanges future = new ModelChanges("future");
        String changeMessage = "test";

        modelManager.addToPastChanges(past);
        modelManager.addToFutureChanges(future);

        expectedModelManager.addToPastChanges(past);
        expectedModelManager.addToPastChanges(new ModelChanges("test"));

        modelManager.commit(changeMessage, new ModelManager());

        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void rollback_noChanges_returnsEmptyOptional() {
        assertTrue(modelManager.rollback().isEmpty());
    }

    @Test
    public void rollback_hasChanges_returnsStringOptional() {
        ModelChanges changes = new ModelChanges("test");

        modelManager.addToPastChanges(changes);
        expectedModelManager.addToFutureChanges(changes);

        Optional<String> returnedMessage = modelManager.rollback();

        assertTrue(returnedMessage.isPresent());
        assertEquals(changes.getChangeMessage(), returnedMessage.get());
        assertEquals(expectedModelManager, modelManager);
    }

    @Test
    public void migrate_noChanges_returnsEmptyOptional() {
        assertTrue(modelManager.migrate().isEmpty());
    }

    @Test
    public void migrate_hasChanges_returnsStringOptional() {
        ModelChanges changes = new ModelChanges("test");
        modelManager.addToFutureChanges(changes);
        expectedModelManager.addToPastChanges(changes);

        Optional<String> returnedMessage = modelManager.migrate();
        assertTrue(returnedMessage.isPresent());
        assertEquals(changes.getChangeMessage(), returnedMessage.get());
        assertEquals(modelManager, expectedModelManager);
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
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertFalse(modelManager.equals(1));

        // different mooLah -> returns false
        assertNotEquals(modelManager, new ModelManager(differentMooLah, userPrefs, modelHistory));

        // different filteredList -> returns false
        String[] keywords = ANNIVERSARY.getDescription().fullDescription.split("\\s+");
        modelManager.updateFilteredExpenseList(new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(mooLah, userPrefs, modelHistory));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMooLahFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(mooLah, differentUserPrefs, modelHistory));

        // different history -> returns false
        ModelHistory diffHistory = new ModelHistory(makeModelChangesStack("dummy"), makeModelChangesStack());
        modelManagerCopy.setModelHistory(diffHistory);
        assertNotEquals(modelManager, modelManagerCopy);
    }
}

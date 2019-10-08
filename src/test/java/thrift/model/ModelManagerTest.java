package thrift.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;
import static thrift.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import thrift.commons.core.GuiSettings;
import thrift.logic.commands.AddExpenseCommand;
import thrift.model.transaction.DescriptionContainsKeywordsPredicate;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.ThriftBuilder;
import thrift.testutil.TypicalTransactions;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Thrift(), new Thrift(modelManager.getThrift()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setThriftFilePath(Paths.get("thrift/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setThriftFilePath(Paths.get("new/thrift/file/path"));
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
    public void setThriftFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setThriftFilePath(null));
    }

    @Test
    public void setThriftFilePath_validPath_setsThriftFilePath() {
        Path path = Paths.get("thrift/file/path");
        modelManager.setThriftFilePath(path);
        assertEquals(path, modelManager.getThriftFilePath());
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTransactionList().remove(0));
    }

    @Test
    public void keepTrackCommands_addCommand_success() {
        Expense expense = new ExpenseBuilder().build();
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(expense);
        modelManager.keepTrackCommands(addExpenseCommand);
        assertEquals(addExpenseCommand, modelManager.getPreviousUndoableCommand());
    }

    @Test
    public void equals() {
        Thrift thrift = new ThriftBuilder().withTransaction(TypicalTransactions.LAKSA).build();
        Thrift differentThrift = new Thrift();
        UserPrefs userPrefs = new UserPrefs();
        PastUndoableCommands pastUndoableCommands = new PastUndoableCommands();

        // same values -> returns true
        modelManager = new ModelManager(thrift, userPrefs, pastUndoableCommands);
        ModelManager modelManagerCopy = new ModelManager(thrift, userPrefs, pastUndoableCommands);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different thrift -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentThrift, userPrefs, pastUndoableCommands)));

        // different filteredList -> returns false
        String[] keywords = TypicalTransactions.PENANG_LAKSA.getDescription().toString().split("\\s+");
        modelManager.updateFilteredTransactionList(new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(thrift, userPrefs, pastUndoableCommands)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setThriftFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(thrift, differentUserPrefs, pastUndoableCommands)));
    }
}

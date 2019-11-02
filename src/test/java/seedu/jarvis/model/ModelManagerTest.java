package seedu.jarvis.model;

import static seedu.jarvis.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.CommandStub;
import seedu.jarvis.testutil.CommandStubExecutionThrowsCommandException;
import seedu.jarvis.testutil.CommandStubHasNoInverse;
import seedu.jarvis.testutil.CommandStubInverseExecutionThrowsCommandException;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        Assertions.assertEquals(new HistoryManager(), modelManager.getHistoryManager());
        Assertions.assertEquals(new FinanceTracker(), modelManager.getFinanceTracker());
        Assertions.assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        Assertions.assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        Assertions.assertEquals(new Planner(), modelManager.getPlanner());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        Assertions.assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        Assertions.assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        Assertions.assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    /**
     * Tests that getting the {@code HistoryManager} from {@code ModelManager} works.
     */
    @Test
    public void getHistoryManager_emptyHistoryManager() {
        HistoryManager historyManager = new HistoryManager();
        historyManager.rememberExecutedCommand(new CommandStub());
        CcaTracker ccaTracker = new CcaTracker();
        modelManager = new ModelManager(ccaTracker, historyManager, new FinanceTracker(), new UserPrefs(),
                new Planner(), new CoursePlanner());
        Assertions.assertEquals(historyManager, modelManager.getHistoryManager());
    }

    /**
     * Tests that setting the {@code HistoryManager} to null throws a {@code NullPointerException}.
     */
    @Test
    public void setHistoryManager_nullHistoryManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setHistoryManager(null));
    }

    /**
     * Tests that setting the {@code HistoryManager} to another {@code HistoryManager} works.
     */
    @Test
    public void setHistoryManager_validHistoryManager_setsHistoryManager() {
        HistoryManager historyManager = new HistoryManager();
        historyManager.rememberExecutedCommand(new CommandStub());
        modelManager.setHistoryManager(historyManager);
        Assertions.assertEquals(historyManager, modelManager.getHistoryManager());
    }

    /**
     * Tests that {@code ModelManager} gets the correct number of available executed commands.
     */
    @Test
    public void getAvailableNumberOfExecutedCommands() {
        Assertions.assertEquals(0, modelManager.getAvailableNumberOfExecutedCommands());
        modelManager.rememberExecutedCommand(new CommandStub());
        Assertions.assertEquals(1, modelManager.getAvailableNumberOfExecutedCommands());
    }

    /**
     * Tests that {@code ModelManager} gets the correct number of available inversely executed commands.
     */
    @Test
    public void getAvailableNumberOfInverselyExecutedCommands() {
        Assertions.assertEquals(0, modelManager.getAvailableNumberOfInverselyExecutedCommands());
        modelManager.rememberExecutedCommand(new CommandStub());
        modelManager.rollback();
        Assertions.assertEquals(1, modelManager.getAvailableNumberOfInverselyExecutedCommands());
    }

    /**
     * Tests that {@code ModelManager} correctly checks if it can roll back commands.
     */
    @Test
    public void canRollback() {
        Assertions.assertFalse(modelManager.canRollback());
        modelManager.rememberExecutedCommand(new CommandStub());
        Assertions.assertTrue(modelManager.canRollback());
    }

    /**
     * Tests that {@code ModelManager} correctly checks if it can commit commands.
     */
    @Test
    public void canCommit() {
        Assertions.assertFalse(modelManager.canCommit());
        modelManager.rememberExecutedCommand(new CommandStub());
        modelManager.rollback();
        Assertions.assertTrue(modelManager.canCommit());
    }

    /**
     * Tests that remembering a command that has no inverse will throw a {@code CommandNotInvertibleException}.
     */
    @Test
    public void rememberExecutedCommand_commandNotInvertible_commandNotInvertibleExceptionThrown() {
        assertThrows(CommandNotInvertibleException.class, () -> modelManager.rememberExecutedCommand(
                new CommandStubHasNoInverse()));
    }

    /**
     * Tests that remembering a command that has an inverse is successful.
     */
    @Test
    public void rememberExecutedCommand_success() {
        modelManager.rememberExecutedCommand(new CommandStub());
        Assertions.assertEquals(1, modelManager.getAvailableNumberOfExecutedCommands());
    }

    /**
     * Tests that false is returned if the command's inverse execution throws a {@code CommandException} during a
     * rollback.
     */
    @Test
    public void rollback_badInverseExecution_returnsFalse() {
        modelManager.rememberExecutedCommand(new CommandStubInverseExecutionThrowsCommandException());
        Assertions.assertFalse(modelManager.rollback());
    }

    /**
     * Tests that true is returned if a rollback was successful.
     */
    @Test
    public void rollback_success_returnsTrue() {
        modelManager.rememberExecutedCommand(new CommandStub());
        Assertions.assertTrue(modelManager.rollback());
    }

    /**
     * Tests that false is returned if the command's execution throws a {@code CommandException} during a commit.
     */
    @Test
    public void commit_badExecution_returnsFalse() {
        modelManager.rememberExecutedCommand(new CommandStubExecutionThrowsCommandException());
        modelManager.rollback();
        Assertions.assertFalse(modelManager.commit());
    }

    /**
     * Tests that true is returned if a commit was successful.
     */
    @Test
    public void commit_success_returnsTrue() {
        modelManager.rememberExecutedCommand(new CommandStub());
        modelManager.rollback();
        Assertions.assertTrue(modelManager.commit());
    }

    @Test
    public void equals() {
        CcaTracker ccaTracker = new CcaTracker();
        HistoryManager historyManager = new HistoryManager();
        historyManager.rememberExecutedCommand(new CommandStub());
        HistoryManager differentHistoryManager = new HistoryManager();
        FinanceTracker financeTracker = new FinanceTracker();
        UserPrefs userPrefs = new UserPrefs();
        Planner planner = new Planner();
        CoursePlanner coursePlanner = new CoursePlanner();

        // same values -> returns true
        modelManager = new ModelManager(ccaTracker, historyManager, financeTracker, userPrefs, planner, coursePlanner);
        ModelManager modelManagerCopy = new ModelManager(ccaTracker, historyManager, financeTracker, userPrefs, planner,
                coursePlanner);
        Assertions.assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        Assertions.assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        Assertions.assertFalse(modelManager.equals(null));

        // different types -> returns false
        Assertions.assertFalse(modelManager.equals(5));

        // different historyManager -> returns false
        Assertions.assertFalse(modelManager.equals(new ModelManager(ccaTracker, differentHistoryManager, financeTracker,
                userPrefs, planner, coursePlanner)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        Assertions.assertFalse(modelManager.equals(new ModelManager(ccaTracker, historyManager, financeTracker,
                differentUserPrefs, planner, coursePlanner)));
    }

}

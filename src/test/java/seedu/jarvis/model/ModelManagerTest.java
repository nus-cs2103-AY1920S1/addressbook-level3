package seedu.jarvis.model;

import static seedu.jarvis.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalPersons.ALICE;
import static seedu.jarvis.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.person.NameContainsKeywordsPredicate;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        Assertions.assertEquals(new HistoryManager(), modelManager.getHistoryManager());
        Assertions.assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        Assertions.assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        Assertions.assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
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
        Assertions.assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
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
        modelManager = new ModelManager(historyManager, new AddressBook(), new UserPrefs(), new Planner());
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
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        Assertions.assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        Assertions.assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        Assertions.assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        HistoryManager historyManager = new HistoryManager();
        historyManager.rememberExecutedCommand(new CommandStub());
        HistoryManager differentHistoryManager = new HistoryManager();
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        Planner planner = new Planner();

        // same values -> returns true
        modelManager = new ModelManager(historyManager, addressBook, userPrefs, planner);
        ModelManager modelManagerCopy = new ModelManager(historyManager, addressBook, userPrefs, planner);
        Assertions.assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        Assertions.assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        Assertions.assertFalse(modelManager.equals(null));

        // different types -> returns false
        Assertions.assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        Assertions.assertFalse(modelManager.equals(new ModelManager(historyManager, differentAddressBook,
                userPrefs, planner)));

        // different historyManager -> returns false
        Assertions.assertFalse(modelManager.equals(new ModelManager(differentHistoryManager, addressBook,
                userPrefs, planner)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        Assertions.assertFalse(modelManager.equals(new ModelManager(historyManager, addressBook, userPrefs, planner)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        Assertions.assertFalse(modelManager.equals(new ModelManager(historyManager, addressBook,
                differentUserPrefs, planner)));
    }

    /**
     * {@code CommandStub} to be added {@code ModelManager}.
     * {@code CommandStub} returns true when checked for having an inverse execution.
     * {@code CommandStub} returns null and does nothing to {@code Model} when {@code CommandStub#execute(Model)} or
     * {@code CommandStub#executeInverse(Model)} is called.
     */
    private static class CommandStub extends Command {
        @Override
        public boolean hasInverseExecution() {
            return true;
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            return null;
        }

        @Override
        public CommandResult executeInverse(Model model) throws CommandException {
            return null;
        }

    }

    /**
     * {@code CommandStubHasNoInverse} is a stub class for {@code Command} which should fail when added to
     * {@code ModelManager} as it has no inverse.
     * {@code CommandStubHasNoInverse} returns false when checked for having an inverse execution.
     */
    private static class CommandStubHasNoInverse extends CommandStub {
        @Override
        public boolean hasInverseExecution() {
            return false;
        }
    }

    /**
     * {@code CommandStubExecutionThrowsCommandException} is a stub class for {@code Command} that will always throw a
     * {@code CommandException} when executed.
     * {@code CommandStubExecutionThrowsCommandException} returns true when checked for having an inverse execution.
     */
    private static class CommandStubExecutionThrowsCommandException extends CommandStub {
        @Override
        public CommandResult execute(Model model) throws CommandException {
            throw new CommandException("CommandException always thrown.");
        }
    }
    /**
     * {@code CommandStubInverseExecutionThrowsCommandException} is a stub class for command that will always throw a
     * {@code CommandException} when inversely executed.
     * {@code CommandStubInverseExecutionThrowsCommandException} returns true when checked for having an inverse
     * execution.
     */
    private static class CommandStubInverseExecutionThrowsCommandException extends CommandStub {
        @Override
        public CommandResult executeInverse(Model model) throws CommandException {
            throw new CommandException("CommandException always thrown.");
        }
    }

}

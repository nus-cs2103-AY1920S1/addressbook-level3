package seedu.jarvis.model.history;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandDeque;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;

/**
 * Tests the behaviour of {@code HistoryManagerTest}.
 */
public class HistoryManagerTest {
    private HistoryManager historyManager;
    private ModelStubAllowingRollbacksAndCommits model;


    /**
     * Initializes {@code historyManager} field as a new {@code HistoryManager} object before each test.
     * Initializes {@code model} field as a new {@code ModelStubAllowingRollbacksAndCommits} object, which is a stub
     * for {@code Model} that allows remember invertible commands, rollbacks and commits.
     */
    @BeforeEach
    public void setUp() {
        historyManager = new HistoryManager();
        model = new ModelStubAllowingRollbacksAndCommits(historyManager);

    }

    /**
     * Tests the construction of {@code HistoryManager} objects.
     */
    @Test
    public void constructor() {
        assertNotNull(historyManager);

        HistoryManager otherHistoryManager = new HistoryManager();
        otherHistoryManager.rememberExecutedCommand(new CommandStub());
        historyManager = new HistoryManager(otherHistoryManager);
        assertEquals(historyManager, otherHistoryManager);
    }

    /**
     * Tests that range of {@code HistoryManager} is equal to the size limit of {@code CommandDeque}.
     */
    @Test
    public void getHistoryRange() {
        assertEquals(CommandDeque.getSizeLimit(), HistoryManager.getHistoryRange());
    }

    /**
     * Tests that getting the executed commands from {@code HistoryManager} works.
     */
    @Test
    public void getExecutedCommands() {
        CommandDeque commandDeque = new CommandDeque();
        IntStream.range(0, HistoryManager.getHistoryRange()).mapToObj(index -> new CommandStub()).forEach(command -> {
            commandDeque.addLatestCommand(command);
            historyManager.rememberExecutedCommand(command);
        });
        assertEquals(commandDeque, historyManager.getExecutedCommands());
    }

    /**
     * Tests that getting the inversely executed commands from {@code HistoryManager} works.
     */
    @Test
    public void getInverselyExecutedCommands() {
        CommandDeque commandDeque = new CommandDeque();
        IntStream.range(0, HistoryManager.getHistoryRange()).mapToObj(index -> new CommandStub()).forEach(command -> {
            commandDeque.addLatestCommand(command);
            historyManager.rememberInverselyExecutedCommand(command);
        });
        assertEquals(commandDeque, historyManager.getInverselyExecutedCommands());
    }

    /**
     * Tests {@code HistoryManager#getNumberOfExecutedCommands()} gets the correct number of executed commands.
     */
    @Test
    public void getNumberOfExecutedCommands() {
        assertEquals(0, historyManager.getNumberOfAvailableExecutedCommands());
        assertDoesNotThrow(() -> historyManager.rememberExecutedCommand(new CommandStub()));
        assertEquals(1, historyManager.getNumberOfAvailableExecutedCommands());
    }


    /**
     * Tests {@code HistoryManager#getNumberOfInverselyExecutedCommands()} gets the correct number of inversely executed
     * commands.
     */
    @Test
    public void getNumberOfInverselyExecutedCommands() {
        assertEquals(0, historyManager.getNumberOfAvailableInverselyExecutedCommands());
        assertDoesNotThrow(() -> historyManager.rememberInverselyExecutedCommand(new CommandStub()));
        assertEquals(1, historyManager.getNumberOfAvailableInverselyExecutedCommands());
    }

    /**
     * Tests that {@code HistoryManager} correctly checks if it can roll back commands.
     */
    @Test
    public void canRollback() {
        assertFalse(historyManager.canRollback());
        historyManager.rememberExecutedCommand(new CommandStub());
        assertTrue(historyManager.canRollback());
    }

    /**
     * Tests that {@code HistoryManager} correctly checks if it can commit commands.
     */
    @Test
    public void canCommit() {
        assertFalse(historyManager.canCommit());
        historyManager.rememberExecutedCommand(new CommandStub());
        historyManager.rollback(model);
        assertTrue(historyManager.canCommit());
    }

    /**
     * Tests that {@code HistoryManager#resetData()} will set the data of the current {@code HistoryManager} to be the
     * same as the {@code HistoryManager} given as argument.
     */
    @Test
    public void resetData() {
        HistoryManager otherHistoryManager = new HistoryManager();
        int numberOfCommands = HistoryManager.getHistoryRange();
        IntStream.range(0, numberOfCommands)
                .mapToObj(index -> new CommandStub())
                .forEach(otherHistoryManager::rememberExecutedCommand);
        IntStream.range(0, numberOfCommands)
                .mapToObj(index -> new CommandStub())
                .forEach(otherHistoryManager::rememberInverselyExecutedCommand);
        historyManager.resetData(otherHistoryManager);
        assertEquals(historyManager.getExecutedCommands(), otherHistoryManager.getExecutedCommands());
        assertEquals(historyManager.getInverselyExecutedCommands(), otherHistoryManager.getInverselyExecutedCommands());
        assertEquals(historyManager, otherHistoryManager);
    }


    /**
     * Tests that {@code CommandNotInvertibleException} is thrown when calling
     * {@code HistoryManager#rememberExecutedCommand} on a command that has no inverse.
     */
    @Test
    public void rememberExecutedCommand_notInvertibleCommand_throwsCommandNotInvertibleException() {
        CommandStubHasNoInverse commandStubHasNoInverse = new CommandStubHasNoInverse();
        assertThrows(CommandNotInvertibleException.class, () -> historyManager.rememberExecutedCommand(
                commandStubHasNoInverse));
    }

    /**
     * Tests that calling {@code HistoryManager#rememberExecutedCommand(Command)} on a command with an inverse will
     * successfully add the command to {@code HistoryManager}.
     */
    @Test
    public void rememberExecutedCommand_success() {
        CommandStub commandStub = new CommandStub();
        assertEquals(0, historyManager.getNumberOfAvailableExecutedCommands());
        assertDoesNotThrow(() -> historyManager.rememberExecutedCommand(commandStub));
        assertEquals(1, historyManager.getNumberOfAvailableExecutedCommands());
    }

    /**
     * Tests that {@code CommandNotInvertibleException} is thrown when calling
     * {@code HistoryManager#rememberInverselyExecutedCommand(Command} on a command that has no inverse.
     */
    @Test
    public void rememberInverselyExecutedCommand_notInvertibleCommand_throwsCommandNotInvertibleException() {
        CommandStubHasNoInverse commandStubHasNoInverse = new CommandStubHasNoInverse();
        assertThrows(CommandNotInvertibleException.class, () -> historyManager.rememberInverselyExecutedCommand(
                commandStubHasNoInverse));
    }
    /**
     * Tests that calling {@code HistoryManager#rememberInverselyExecutedCommand(Command)} on a command with an inverse
     * will successfully add the command to {@code HistoryManager}.
     */
    @Test
    public void rememberInverselyExecutedCommand_success() {
        CommandStub commandStub = new CommandStub();
        assertEquals(0, historyManager.getNumberOfAvailableInverselyExecutedCommands());
        assertDoesNotThrow(() -> historyManager.rememberInverselyExecutedCommand(commandStub));
        assertEquals(1, historyManager.getNumberOfAvailableInverselyExecutedCommands());
    }

    /**
     * Tests that {@code HistoryManager} deletes all inversely executed commands that it was storing when
     * {@code HistoryManager#forgetAllInverselyExecutedCommands()} is called.
     */
    @Test
    public void forgetAllInverselyExecutedCommands() {
        int numberOfCommands = HistoryManager.getHistoryRange();
        IntStream.range(0, numberOfCommands)
                .mapToObj(index -> new CommandStub())
                .forEach(historyManager::rememberInverselyExecutedCommand);
        assertEquals(numberOfCommands, historyManager.getNumberOfAvailableInverselyExecutedCommands());
        historyManager.forgetAllInverselyExecutedCommands();
        assertEquals(0, historyManager.getNumberOfAvailableInverselyExecutedCommands());
    }

    /**
     * Tests that an unsuccessful rollback returns false.
     */
    @Test
    public void rollback_badExecution_evaluatesToFalse() {
        CommandStubInverseExecutionThrowsCommandException invalidCommand =
                new CommandStubInverseExecutionThrowsCommandException();
        model.rememberExecutedCommand(invalidCommand);
        assertFalse(historyManager.rollback(model));
    }

    /**
     * Tests that a successful rollback returns true.
     */
    @Test
    public void rollback_success() {
        CommandStub validCommand = new CommandStub();
        model.rememberExecutedCommand(validCommand);
        assertTrue(historyManager.rollback(model));
    }

    /**
     * Tests that a unsuccessful commit returns false.
     */
    @Test
    public void commit_badExecution_evaluatesToFalse() {
        CommandStubExecutionThrowsCommandException invalidCommand = new CommandStubExecutionThrowsCommandException();
        model.rememberExecutedCommand(invalidCommand);
        historyManager.rollback(model);
        assertFalse(historyManager.commit(model));
    }

    /**
     * Tests that a successful commit returns true.
     */
    @Test
    public void commit_success() {
        CommandStub validCommand = new CommandStub();
        model.rememberExecutedCommand(validCommand);
        historyManager.rollback(model);
        assertTrue(historyManager.commit(model));
    }

    /**
     * Tests that {@code HistoryManager} checks for equality correctly with other Objects.
     */
    @Test
    public void equals() {
        HistoryManager otherHistoryManager = new HistoryManager();
        // both empty -> true.
        assertTrue(historyManager.equals(otherHistoryManager));

        List<Command> executedCommands = new ArrayList<>();
        List<Command> inverselyExecutedCommands = new ArrayList<>();
        IntStream.range(0, HistoryManager.getHistoryRange())
                .mapToObj(index -> new CommandStub())
                .forEach(executedCommands::add);
        IntStream.range(0, HistoryManager.getHistoryRange())
                .mapToObj(index -> new CommandStub())
                .forEach(inverselyExecutedCommands::add);

        executedCommands.forEach(otherHistoryManager::rememberExecutedCommand);
        // difference in executed commands -> false.
        assertFalse(historyManager.equals(otherHistoryManager));
        executedCommands.forEach(historyManager::rememberExecutedCommand);
        // same executed commands -> false.
        assertTrue(historyManager.equals(otherHistoryManager));

        inverselyExecutedCommands.forEach(otherHistoryManager::rememberInverselyExecutedCommand);
        // difference in inversely executed commands -> false.
        assertFalse(historyManager.equals(otherHistoryManager));
        inverselyExecutedCommands.forEach(historyManager::rememberInverselyExecutedCommand);
        // same inversely executed commands -> false.
        assertTrue(historyManager.equals(otherHistoryManager));

        otherHistoryManager.forgetAllInverselyExecutedCommands();
        // same executed commands, different inversely executed commands -> false.
        assertFalse(historyManager.equals(otherHistoryManager));
    }


    /**
     * {@code CommandStub} to be added to {@code HistoryManager}.
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
     * {@code HistoryManager} as it has no inverse.
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
     * {@code CommandStubInverseExecutionThrowsCommandException} is a stub class for {@code Command} that will always
     * throw a {@code CommandException} when inversely executed.
     * {@code CommandStubInverseExecutionThrowsCommandException} returns true when checked for having an inverse
     * execution.
     */
    private static class CommandStubInverseExecutionThrowsCommandException extends CommandStub {
        @Override
        public CommandResult executeInverse(Model model) throws CommandException {
            throw new CommandException("CommandException always thrown.");
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HistoryManager getHistoryManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHistoryManager(HistoryManager historyManager) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getAvailableNumberOfExecutedCommands() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getAvailableNumberOfInverselyExecutedCommands() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRollback() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canCommit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void rememberExecutedCommand(Command command) throws CommandNotInvertibleException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean rollback() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean commit() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * {@code ModelStubAllowingRollbacksAndCommits} is a stub class for {@code Model} that allows remembering commands,
     * rollbacks and commits.
     */
    private static class ModelStubAllowingRollbacksAndCommits extends ModelStub {
        private HistoryManager historyManager;

        public ModelStubAllowingRollbacksAndCommits(HistoryManager historyManager) {
            this.historyManager = historyManager;
        }

        @Override
        public void rememberExecutedCommand(Command command) {
            historyManager.rememberExecutedCommand(command);
        }

        @Override
        public boolean rollback() {
            return historyManager.rollback(this);
        }

        @Override
        public boolean commit() {
            return historyManager.commit(this);
        }
    }

}

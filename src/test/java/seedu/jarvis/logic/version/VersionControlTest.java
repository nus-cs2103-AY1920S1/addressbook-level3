package seedu.jarvis.logic.version;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.address.DeleteAddressCommand;
import seedu.jarvis.logic.commands.address.ListAddressCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.testutil.PersonBuilder;

/**
 * Tests VersionControl Singleton.
 */
public class VersionControlTest {
    private VersionControl versionControl = VersionControl.INSTANCE;
    private Model model = new ModelManager();

    /**
     * Resets the singleton instance and model to its initial state before each test.
     */
    @BeforeEach
    public void resetInstanceBeforeTest() {
        versionControl.hardReset();
        model = new ModelManager();
    }

    /**
     * Resets the singleton instance and model to its initial state after each test.
     */
    @AfterEach
    public void resetInstanceAfterTest() {
        versionControl.hardReset();
        model = new ModelManager();
    }

    /**
     * Verifies that instance is the same when accessing singleton.
     */
    @Test
    public void test_singleton() {
        VersionControl instance = VersionControl.INSTANCE;
        assertSame(versionControl, instance);
    }

    /**
     * Verifies that adding command that has no inverse will throw the correct exception.
     */
    @Test
    public void addInvertibleCommand_notInvertible_exceptionThrown() {
        Command listAddressCommand = new ListAddressCommand();
        assertThrows(CommandNotInvertibleException.class, () -> versionControl.addExecutedCommand(listAddressCommand));
    }

    /**
     * Verifies that the operation of adding executed commands work.
     */
    @Test
    public void addExecutedCommand_success() {
        Command addAddressCommand = new AddAddressCommand(new PersonBuilder().build());
        assertDoesNotThrow(() -> versionControl.addExecutedCommand(addAddressCommand));
    }

    /**
     * Verifies that adding a duplicate command will throw the correct exception if it is in executed commands.
     */
    @Test
    public void addExecutedCommand_duplicate_exceptionThrown() {
        Command addAddressCommand = new AddAddressCommand(new PersonBuilder().build());
        assertDoesNotThrow(() -> versionControl.addExecutedCommand(addAddressCommand));
        assertThrows(DuplicateCommandException.class, () -> versionControl.addExecutedCommand(addAddressCommand));
    }

    /**
     * Verifies that the operation of adding inversely executed commands work.
     */
    @Test
    public void addInverselyExecutedCommand_success() {
        Command addAddressCommand = new AddAddressCommand(new PersonBuilder().build());
        assertDoesNotThrow(() -> versionControl.addInverselyExecutedCommand(addAddressCommand));
    }

    /**
     * Verifies that adding a duplicate command will throw the correct exception if it is in inversely executed
     * commands.
     */
    @Test
    public void addInverselyExecutedCommand_duplicate_exceptionThrown() {
        Command addAddressCommand = new AddAddressCommand(new PersonBuilder().build());
        assertDoesNotThrow(() -> versionControl.addInverselyExecutedCommand(addAddressCommand));
        assertThrows(DuplicateCommandException.class, () ->
                versionControl.addInverselyExecutedCommand(addAddressCommand));
    }

    /**
     * Verifies that adding duplicate command will throw the correct exception if they are stored in executed
     * commands or inversely executed commands.
     */
    @Test
    public void test_duplicatesInExecutedAndInverselyExecutedCommands() {
        Command addAddressCommand = new AddAddressCommand(new PersonBuilder().build());
        assertDoesNotThrow(() -> versionControl.addInverselyExecutedCommand(addAddressCommand));
        assertThrows(DuplicateCommandException.class, () -> versionControl.addExecutedCommand(addAddressCommand));

        versionControl.hardReset();

        assertDoesNotThrow(() -> versionControl.addExecutedCommand(addAddressCommand));
        assertThrows(DuplicateCommandException.class, () ->
                versionControl.addInverselyExecutedCommand(addAddressCommand));
    }

    /**
     * Verifies that the undoing a command that is not in version control throws the correct exception.
     */
    @Test
    public void undo_commandNotFound_exceptionThrown() {
        assertThrows(CommandNotFoundException.class, () -> versionControl.undo(model));
    }

    /**
     * Verifies that undoing a command that causes a CommandException will throw it when undoing it.
     */
    @Test
    public void undo_command_exceptionThrown() {
        Command addAddressCommand = new AddAddressCommand(new PersonBuilder().build());
        assertDoesNotThrow(() -> versionControl.addExecutedCommand(addAddressCommand));
        assertThrows(CommandException.class, () -> versionControl.undo(model));
    }

    /**
     * Verifies that the undo operation works as intended.
     */
    @Test
    public void undo_success() {
        Person person = new PersonBuilder().build();
        Command addAddressCommand = new AddAddressCommand(person);
        assertDoesNotThrow(() -> addAddressCommand.execute(model));
        assertDoesNotThrow(() -> versionControl.addExecutedCommand(addAddressCommand));
        assertDoesNotThrow(() -> versionControl.undo(model));
        assertFalse(model.hasPerson(person));
    }

    /**
     * Verifies that redoing a command that is not in version control will throw the correct exception.
     */
    @Test
    public void redo_commandNotFound_exceptionThrown() {
        assertThrows(CommandNotFoundException.class, () -> versionControl.redo(model));
    }

    /**
     * Verifies that redoing a command that throws a CommandException will throw it when redoing it.
     */
    @Test
    public void redo_command_exceptionThrown() {
        Command deleteAddressCommand = new DeleteAddressCommand(Index.fromZeroBased(0));
        assertDoesNotThrow(() -> versionControl.addInverselyExecutedCommand(deleteAddressCommand));
        assertThrows(CommandException.class, () -> versionControl.redo(model));
    }

    /**
     * Verifies that the redo operation works as intended.
     */
    @Test
    public void redo_success() {
        Person person = new PersonBuilder().build();
        Command addAddressCommand = new AddAddressCommand(person);
        assertDoesNotThrow(() -> addAddressCommand.execute(model));
        assertDoesNotThrow(() -> versionControl.addExecutedCommand(addAddressCommand));
        assertDoesNotThrow(() -> versionControl.undo(model));
        assertFalse(model.hasPerson(person));
        assertDoesNotThrow(() -> versionControl.redo(model));
        assertTrue(model.hasPerson(person));
    }
}

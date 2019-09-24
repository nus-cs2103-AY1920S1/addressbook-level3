package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;

/**
 * Tests the functionality of {@code CommandCache}.
 */
public class CommandCacheTest {

    /**
     * Verifies that {@code CommandCache#addLatestCommand(Command)} adds the command correctly into the deque.
     */
    @Test
    public void addLatestCommand_success() {
        CommandCache commandCache = new CommandCache();
        Command command = new ClearAddressCommand();
        assertDoesNotThrow(() -> commandCache.addLatestCommand(command)); // tests a successful add.
        assertSame(commandCache.getLatestCommand(), command); // making sure the command was added correctly.
    }


    /**
     * Verifies that {@code CommandCache#addLatestCommand(Command)} does not allow adding the same command object
     * instance to the deque while it already is inside it.
     */
    @Test
    public void addLatestCommand_duplicateCommand_exceptionThrown() {
        CommandCache commandCache = new CommandCache();
        Command command = new ClearAddressCommand();

        // adds command the first time.
        assertDoesNotThrow(() -> commandCache.addLatestCommand(command));

        // adds the same command and checks that the correct exception is thrown.
        assertThrows(DuplicateCommandException.class, () -> commandCache.addLatestCommand(command));
    }

    /**
     * Verifies that {@code CommandCache#deleteOldestCommand()} removes the correct command successfully.
     */
    @Test
    public void deleteOldestCommand_nonEmpty_success() {
        CommandCache commandCache = new CommandCache();
        Command c1 = new ClearAddressCommand();
        Command c2 = new ClearAddressCommand();
        assertDoesNotThrow(() -> {
            commandCache.addLatestCommand(c1);
            commandCache.addLatestCommand(c2);
        });
        assertEquals(2, commandCache.getSize()); // checks that commands were added.
        assertSame(commandCache.deleteOldestCommand(), c1); // checks that the correct command is deleted.
        assertEquals(1, commandCache.deleteOldestCommand()); // checks that the size reduced by 1.
        assertSame(commandCache.deleteOldestCommand(), c2); // checks that the correct command is deleted.
        assertEquals(0, commandCache.deleteOldestCommand()); // checks that the size reduced by 1.
    }

    /**
     * Verifies that {@code CommandCache#deleteOldestCommand()} returns null if it is empty.
     */
    @Test
    public void deleteOldestCommand_empty_success() {
        CommandCache commandCache = new CommandCache();

        // checks that null is returned if there is no command to delete.
        assertNull(commandCache.deleteOldestCommand());
    }

    /**
     * Verifies that {@code CommandCache} is keeping within its size limit when it is adding commands.
     */
    @Test
    public void test_sizeLimit() {
        CommandCache commandCache = new CommandCache();

        // adds twice the amount of commands allowed by its size limit.
        for (int i = 0; i < commandCache.getLimit() * 2; ++i) {
            assertDoesNotThrow(() -> commandCache.addLatestCommand(new ClearAddressCommand()));
        }

        assertEquals(commandCache.getLimit(), commandCache.getSize()); // checks that it is size has not exceeded limit.
    }

    /**
     * Verifies that {@code CommandCache} is keeping within its size limit when it is adding commands
     */
    @Test
    public void test_resizing() {
        CommandCache commandCache = new CommandCache();

        // adds commands until it is at the size limit.
        for (int i = 0; i < commandCache.getLimit(); ++i) {
            assertDoesNotThrow(() -> commandCache.addLatestCommand(new ClearAddressCommand()));
        }

        int updatedLimit = commandCache.getLimit() / 2;
        commandCache.setLimit(updatedLimit);

        assertEquals(updatedLimit, commandCache.getLimit()); // checks if limit is updated.
        assertEquals(updatedLimit, commandCache.getSize()); // checks if new limit is enforced.
    }
}

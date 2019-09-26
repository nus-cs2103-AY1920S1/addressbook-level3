package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;

/**
 * Tests the functionality of {@code CommandDeque}.
 */
public class CommandDequeTest {

    /**
     * Verifies that {@code CommandDeque#addLatestCommand(Command)} adds the command correctly into the deque.
     */
    @Test
    public void addLatestCommand_success() {
        CommandDeque commandDeque = new CommandDeque();
        Command command = new ClearAddressCommand();
        assertDoesNotThrow(() -> commandDeque.addLatestCommand(command)); // tests a successful add.
        assertSame(commandDeque.getLatestCommand(), command); // making sure the command was added correctly.
    }


    /**
     * Verifies that {@code CommandDeque#addLatestCommand(Command)} does not allow adding the same command object
     * instance to the deque while it already is inside it.
     */
    @Test
    public void addLatestCommand_duplicateCommand_exceptionThrown() {
        CommandDeque commandDeque = new CommandDeque();
        Command command = new ClearAddressCommand();

        // adds command the first time.
        assertDoesNotThrow(() -> commandDeque.addLatestCommand(command));

        // adds the same command and checks that the correct exception is thrown.
        assertThrows(DuplicateCommandException.class, () -> commandDeque.addLatestCommand(command));
    }

    /**
     * Verifies that {@code CommandDeque#deleteOldestCommand()} removes the correct command successfully.
     */
    @Test
    public void deleteOldestCommand_nonEmpty_success() {
        CommandDeque commandDeque = new CommandDeque();
        Command c1 = new ClearAddressCommand();
        Command c2 = new ClearAddressCommand();
        assertDoesNotThrow(() -> {
            commandDeque.addLatestCommand(c1);
            commandDeque.addLatestCommand(c2);
        });
        assertEquals(2, commandDeque.getSize()); // checks that commands were added.
        assertSame(commandDeque.deleteOldestCommand(), c1); // checks that the correct command is deleted.
        assertEquals(1, commandDeque.getSize()); // checks that the size reduced by 1.
        assertSame(commandDeque.deleteOldestCommand(), c2); // checks that the correct command is deleted.
        assertEquals(0, commandDeque.getSize()); // checks that the size reduced by 1.
    }

    /**
     * Verifies that {@code CommandDeque#deleteOldestCommand()} returns null if it is empty.
     */
    @Test
    public void deleteOldestCommand_empty_success() {
        CommandDeque commandDeque = new CommandDeque();

        // checks that null is returned if there is no command to delete.
        assertNull(commandDeque.deleteOldestCommand());
    }

    /**
     * Verifies that {@code CommandDeque} is keeping within its size limit when it is adding commands.
     */
    @Test
    public void test_sizeLimit() {
        CommandDeque commandDeque = new CommandDeque();

        // adds twice the amount of commands allowed by its size limit.
        IntStream.range(0, commandDeque.getLimit() * 2)
                .forEach(index -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(new ClearAddressCommand())));

        assertEquals(commandDeque.getLimit(), commandDeque.getSize()); // checks that it is size has not exceeded limit.
    }

    /**
     * Verifies that {@code CommandDeque} is keeping within its size limit when it is adding commands
     */
    @Test
    public void test_resizing() {
        CommandDeque commandDeque = new CommandDeque();

        // adds commands until it is at the size limit.
        IntStream.range(0, commandDeque.getLimit())
                .forEach(index -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(new ClearAddressCommand())));

        int updatedLimit = commandDeque.getLimit() / 2;
        commandDeque.setLimit(updatedLimit);

        assertEquals(updatedLimit, commandDeque.getLimit()); // checks if limit is updated.
        assertEquals(updatedLimit, commandDeque.getSize()); // checks if new limit is enforced.
    }

    /**
     * Verifies that {@code CommandDeque} is clear cache will correctly clear all commands stored.
     */
    @Test
    public void test_clearCache() {
        CommandDeque commandDeque = new CommandDeque();

        // adds commands until it is at the size limit.
        IntStream.range(0, commandDeque.getLimit())
                .forEach(index -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(new ClearAddressCommand())));

        int limit = commandDeque.getLimit();

        commandDeque.clearCache();

        assertTrue(commandDeque.isEmpty()); // checks that commands are all cleared.
        assertEquals(limit, commandDeque.getLimit()); // checks that limit is unchanged.

    }
}

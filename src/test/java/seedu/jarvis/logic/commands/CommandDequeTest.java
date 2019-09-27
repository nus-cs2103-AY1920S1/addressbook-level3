package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;
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
     * Verifies that {@code CommandDeque#deleteLatestCommand()} removes the correct command successfully.
     */
    @Test
    public void deleteLatestCommand_nonEmpty_success() {
        CommandDeque commandDeque = new CommandDeque();
        Deque<Command> commands = new ArrayDeque<>();
        int numberOfCommands = 10;
        IntStream.range(0, numberOfCommands)
                .mapToObj(index -> new ClearAddressCommand())
                .forEach(commands::addLast);
        commands.forEach(command -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(command)));
        IntStream.range(0, numberOfCommands)
                .forEach(index -> {
                    Command c = assertDoesNotThrow(commandDeque::deleteLatestCommand);
                    assertSame(c, commands.removeLast());
                });
    }

    /**
     * Verifies that {@code CommandDeque#deletelatestCommand()} throws {@code CommandNotFoundException} if it is empty.
     */
    @Test
    public void deleteLatestCommand_empty_exceptionThrown() {
        CommandDeque commandDeque = new CommandDeque();
        assertThrows(CommandNotFoundException.class, commandDeque::deleteOldestCommand);
    }

    /**
     * Verifies that {@code CommandDeque#deleteOldestCommand()} removes the correct command successfully.
     */
    @Test
    public void deleteOldestCommand_nonEmpty_success() {
        CommandDeque commandDeque = new CommandDeque();
        ArrayDeque<Command> commands = new ArrayDeque<>();
        int numberOfCommands = 10;
        IntStream.range(0, numberOfCommands)
                .mapToObj(index -> new ClearAddressCommand())
                .forEach(commands::addLast);
        commands.forEach(command -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(command)));
        IntStream.range(0, numberOfCommands)
                .forEach(index -> {
                    Command c = assertDoesNotThrow(commandDeque::deleteOldestCommand);
                    assertSame(c, commands.removeFirst());
                });
    }

    /**
     * Verifies that {@code CommandDeque#deleteOldestCommand()} throws {@code CommandNotFoundException} if it is empty.
     */
    @Test
    public void deleteOldestCommand_empty_exceptionThrown() {
        CommandDeque commandDeque = new CommandDeque();
        assertThrows(CommandNotFoundException.class, commandDeque::deleteOldestCommand);
    }

    /**
     * Verifies that {@code CommandDeque} is keeping within its size limit when it is adding commands.
     */
    @Test
    public void test_sizeLimit() {
        CommandDeque commandDeque = new CommandDeque();

        // adds twice the amount of commands allowed by its size limit.
        IntStream.range(0, commandDeque.getSizeLimit() * 2)
                .forEach(index -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(new ClearAddressCommand())));

        // checks that it is size has not exceeded limit.
        assertEquals(commandDeque.getSizeLimit(), commandDeque.getSize());
    }

    /**
     * Verifies that {@code CommandDeque} is keeping within its size limit when it is adding commands
     */
    @Test
    public void test_resizing() {
        CommandDeque commandDeque = new CommandDeque();

        // adds commands until it is at the size limit.
        IntStream.range(0, commandDeque.getSizeLimit())
                .forEach(index -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(new ClearAddressCommand())));

        int updatedLimit = commandDeque.getSizeLimit() / 2;
        commandDeque.setSizeLimit(updatedLimit);

        assertEquals(updatedLimit, commandDeque.getSizeLimit()); // checks if limit is updated.
        assertEquals(updatedLimit, commandDeque.getSize()); // checks if new limit is enforced.
    }

    /**
     * Verifies that {@code CommandDeque} is clear cache will correctly clear all commands stored.
     */
    @Test
    public void test_clearCache() {
        CommandDeque commandDeque = new CommandDeque();

        // adds commands until it is at the size limit.
        IntStream.range(0, commandDeque.getSizeLimit())
                .forEach(index -> assertDoesNotThrow(() -> commandDeque.addLatestCommand(new ClearAddressCommand())));

        int limit = commandDeque.getSizeLimit();

        commandDeque.clearCache();

        assertTrue(commandDeque.isEmpty()); // checks that commands are all cleared.
        assertEquals(limit, commandDeque.getSizeLimit()); // checks that limit is unchanged.

    }
}

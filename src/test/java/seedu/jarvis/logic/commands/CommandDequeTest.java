package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;
import seedu.jarvis.model.Model;

/**
 * Tests the behaviour of {@code CommandDeque}.
 */
public class CommandDequeTest {
    private CommandDeque commandDeque;

    /**
     * Initializes {@code commandDeque} field as a new {@code CommandDeque} object before each test.
     */
    @BeforeEach
    public void setUp() {
        commandDeque = new CommandDeque();
    }

    /**
     * Tests the behaviour of constructing {@code CommandDeque} objects.
     */
    @Test
    public void constructor() {
        assertNotNull(commandDeque);

        CommandDeque otherCommandDeque = new CommandDeque();
        otherCommandDeque.addLatestCommand(new CommandStub());
        commandDeque = new CommandDeque(otherCommandDeque);
        assertEquals(commandDeque, otherCommandDeque);
    }

    /**
     * Tests that getting commands from {@code CommandDeque} objects work.
     */
    @Test
    public void getCommands() {
        List<Command> commands = new ArrayList<>();
        int numberOfCommands = CommandDeque.getSizeLimit();
        IntStream.range(0, numberOfCommands).mapToObj(index -> new CommandStub()).forEach(commands::add);
        commands.forEach(commandDeque::addLatestCommand);

        List<Command> getCommands = commandDeque.getCommands();
        IntStream.range(0, numberOfCommands)
                .forEach(index -> assertEquals(getCommands.get(index), commands.get(index)));
    }

    /**
     * Tests {@code CommandDeque#getSize()} of {@code CommandDeque} objects.
     */
    @Test
    public void getSize() {
        assertEquals(0, commandDeque.getSize());
        commandDeque.addLatestCommand(new CommandStub());
        assertEquals(1, commandDeque.getSize());
        commandDeque.deleteLatestCommand();
        assertEquals(0, commandDeque.getSize());
    }

    /**
     * Tests {@code CommandDeque#isEmpty} of {@code CommandDeque} objects.
     */
    @Test
    public void isEmpty() {
        assertTrue(commandDeque.isEmpty());
        commandDeque.addLatestCommand(new CommandStub());
        assertFalse(commandDeque.isEmpty());
        commandDeque.deleteLatestCommand();
        assertTrue(commandDeque.isEmpty());
    }

    /**
     * Tests resetting data of {@code CommandDeque} objects to other {@code CommandDeque} objects.
     */
    @Test
    public void resetData() {
        CommandDeque otherCommandDeque = new CommandDeque();
        List<Command> commands = new ArrayList<>();
        int numberOfCommands = CommandDeque.getSizeLimit();
        IntStream.range(0, numberOfCommands).mapToObj(index -> new CommandStub()).forEach(commands::add);
        commands.forEach(otherCommandDeque::addLatestCommand);
        commandDeque.resetData(otherCommandDeque);
        assertTrue(commandDeque.equals(otherCommandDeque));

        CommandDeque anotherCommandDeque = new CommandDeque();
        commandDeque.resetData(anotherCommandDeque);
        assertTrue(commandDeque.equals(otherCommandDeque));
    }

    /**
     * Tests that a {@code CommandNotFoundException} is thrown when getting the latest command from an empty
     * {@code CommandDeque} object.
     */
    @Test
    public void getLatestCommand_emptyCommandDeque_throwsCommandNotFoundException() {
        assertThrows(CommandNotFoundException.class, commandDeque::getLatestCommand);
    }

    /**
     * Tests the typical successful path of getting the latest command of an empty {@code CommandDeque} object.
     */
    @Test
    public void getLatestCommand_success() {
        Command commandStub = new CommandStub();
        commandDeque.addLatestCommand(commandStub);
        assertSame(commandStub, commandDeque.getLatestCommand());
    }

    /**
     * Tests that adding a null to a {@code CommandDeque} object throws a {@code NullPointerException}.
     */
    @Test
    public void addLatestCommand_addNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> commandDeque.addLatestCommand(null));
    }

    /**
     * Tests that the typical successful operation of adding a Command to a {@code CommandDeque} object works.
     */
    @Test
    public void addLatestCommand_success() {
        Command commandStub = new CommandStub();
        commandDeque.addLatestCommand(commandStub);
        assertEquals(1, commandDeque.getSize());
        assertSame(commandStub, commandDeque.getLatestCommand());
    }

    /**
     * Tests that a {@code CommandNotFoundException} is thrown when deleting the latest command from an empty
     * {@code CommandDeque} object.
     */
    @Test
    public void deleteLatestCommand_emptyCommandDeque_throwsCommandNotFoundException() {
        assertThrows(CommandNotFoundException.class, commandDeque::deleteLatestCommand);
    }

    /**
     * Tests that the typical successful operation of deleting the latest Command from a {@code CommandDeque} object
     * works.
     */
    @Test
    public void deleteLatestCommand_success() {
        Command dummyCommandStub = new CommandStub();
        Command commandStub = new CommandStub();
        commandDeque.addLatestCommand(dummyCommandStub);
        commandDeque.addLatestCommand(commandStub);
        Command deletedCommand = commandDeque.deleteLatestCommand();
        assertSame(commandStub, deletedCommand);
    }

    /**
     * Tests that a {@code CommandNotFoundException} is thrown when deleting the oldest command from an empty
     * {@code CommandDeque} object.
     */
    @Test
    public void deleteOldestCommand_emptyCommandDeque_throwsCommandNotFoundException() {
        assertThrows(CommandNotFoundException.class, commandDeque::deleteOldestCommand);
    }

    /**
     * Tests that the typical successful operation of deleting the oldest Command from a {@code CommandDeque} object
     * works.
     */
    @Test
    public void deleteOldestCommand_success() {
        Command commandStub = new CommandStub();
        Command dummyCommandStub = new CommandStub();
        commandDeque.addLatestCommand(commandStub);
        commandDeque.addLatestCommand(dummyCommandStub);
        Command deletedCommand = commandDeque.deleteOldestCommand();
        assertSame(commandStub, deletedCommand);
    }

    /**
     * Tests that {@code CommandDeque} object clears all commands.
     */
    @Test
    public void clear() {
        int inputSize = CommandDeque.getSizeLimit();
        IntStream.range(0, inputSize)
                .mapToObj(index -> new CommandStub())
                .forEach(command -> commandDeque.addLatestCommand(command));
        assertEquals(inputSize, commandDeque.getSize());
        commandDeque.clear();
        assertTrue(commandDeque.isEmpty());
    }

    /**
     * Tests that {@code CommandDeque} checks for equality correctly with other Objects.
     */
    @Test
    public void equals() {
        assertTrue(commandDeque.equals(commandDeque)); // same object -> true.
        assertFalse(commandDeque.equals(null)); // null -> false.

        CommandDeque otherCommandDeque = new CommandDeque();
        assertTrue(commandDeque.equals(otherCommandDeque)); // both empty -> true.

        int inputSize = CommandDeque.getSizeLimit();
        List<Command> commandsList = new ArrayList<>(inputSize);
        IntStream.range(0, inputSize).mapToObj(index -> new CommandStub()).forEach(commandsList::add);

        commandsList.forEach(commandDeque::addLatestCommand);
        assertFalse(commandDeque.equals(otherCommandDeque)); // one with commands, one empty -> false.
        commandsList.forEach(otherCommandDeque::addLatestCommand);
        assertTrue(commandDeque.equals(otherCommandDeque)); // both with the same commands -> true.

        List<Command> otherCommandsList = new ArrayList<>(inputSize);
        IntStream.range(0, inputSize).mapToObj(index -> new CommandStub()).forEach(otherCommandsList::add);
        otherCommandsList.forEach(otherCommandDeque::addLatestCommand);
        assertFalse(commandDeque.equals(otherCommandDeque)); // both with different commands -> false.
    }

    /**
     * Tests that a {@code CommandDeque} object maintains its size limit.
     */
    @Test
    public void sizeLimit() {
        int sizeLimit = CommandDeque.getSizeLimit();
        int inputSize = sizeLimit * 100;
        IntStream.range(0, inputSize).mapToObj(index -> new CommandStub()).forEach(commandDeque::addLatestCommand);
        assertEquals(sizeLimit, commandDeque.getSize());
    }

    /**
     * {@code CommandStub} to be added to {@code CommandDeque}.
     * {@code CommandStub} returns true when checked for having an inverse execution.
     * {@code CommandStub} should not be executed or inversely executed and will throw an {@code AssertionError} if it
     * is executed or inversely executed.
     */
    private static class CommandStub extends Command {
        @Override
        public String getCommandWord() {
            throw new AssertionError("This message should not be called.");
        }

        @Override
        public boolean hasInverseExecution() {
            return true;
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            throw new AssertionError("This message should not be called.");
        }

        @Override
        public CommandResult executeInverse(Model model) throws CommandException {
            throw new AssertionError("This message should no be called.");
        }
    }
}

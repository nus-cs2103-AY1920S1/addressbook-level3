package seedu.jarvis.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;

/**
 * A deque of commands that does not allow nulls and ensures that each command object instance can only be inside
 * the deque at most once.
 *
 * The front of the deque stores the latest commands, while the back of the deque stores the oldest commands.
 * The deque is kept within the size given by the value of limit, if the deque exceeds the
 * size, it will remove commands till it is within the size limit, removing the oldest commands first.
 *
 * Command object instances cannot be added more than once to the deque, commands that do the achieve the same purpose
 * but are distinct objects are allowed, only repeated storing of the same command object instance is not allowed.
 */
public class CommandDeque {
    /** Starting default limit to be assigned to each instance. */
    public static final int DEFAULT_INITIAL_SIZE_LIMIT = 10;
    /**
     * Deque to store commands. Deque is used to facilitate adding new commands to the start of the deque, and deleting
     * old commands from the back of the deque.
     */
    private Deque<Command> commands;
    /**
     * Used to track each command instance stored in the deque to help check for attempted adding of instances that
     * are already inside the deque.
     */
    private Set<Command> commandTracker;
    /** Represents the maximum number of commands to store in the deque, that can be updated in value and enforced. */
    private int sizeLimit;

    /**
     * Creates a new {@code CommandDeque} with the default size limit of DEFAULT_INITIAL_SIZE_LIMIT.
     * This size limit can be set to another number.
     */
    public CommandDeque() {
        commands = new ArrayDeque<>();
        commandTracker = new HashSet<>();
        sizeLimit = DEFAULT_INITIAL_SIZE_LIMIT;
    }

    /**
     * Creates a new {@code CommandDeque} with a custom size limit.
     * @param sizeLimit Custom size limit.
     */
    public CommandDeque(int sizeLimit) {
        this();
        this.sizeLimit = sizeLimit;
    }

    /**
     * Number of commands stored in {@code CommandDeque}.
     * @return The number of commands stored in {@code CommandDeque}.
     */
    public int getSize() {
        assert commands.size() == commandTracker.size() : "command disparity between deque and set.";
        return commands.size();
    }

    /**
     * Gets the current command size limit of the {@code CommandDeque}.
     * @return current command size limit of {@code CommandDeque}.
     */
    public int getSizeLimit() {
        return sizeLimit;
    }

    /**
     * Updates the deque size limit
     * Trims the deque to the new size if the new size limit is lower that the original.
     * Returns null if the limit is invalid, which is lesser than zero.
     *
     * @param sizeLimit The new size limit.
     */
    public void setSizeLimit(int sizeLimit) {
        if (sizeLimit < 0) {
            return;
        }
        this.sizeLimit = sizeLimit;
        trimSize();
    }

    /**
     * Returns whether there are any commands stored in {@code CommandDeque}.
     * @return Whether the {@code CommandDeque} is storing any commands.
     */
    public boolean isEmpty() {
        return commands.isEmpty() && commandTracker.isEmpty();
    }

    /**
     * Checks if this command object instance is already stored inside the {@code CommandDeque}.
     *
     * @param command Command object instance to be checked.
     * @return Whether the command is stored inside the {@code CommandDeque}.
     */
    public boolean hasCommand(Command command) {
        return !commands.isEmpty() && commandTracker.contains(command);
    }

    /**
     * Gets the most recent {@code Command} added to {@code CommandDeque} which is at the front of the deque.
     * Returns null if the {@code CommandDeque} is empty.
     *
     * @return The most recent {@code Command} added to {@code CommandDeque}, returns null if
     * {@code CommandDeque#isEmpty()} is true.
     */
    public Command getLatestCommand() {
        if (isEmpty()) {
            return null;
        }
        return commands.getFirst();
    }

    /**
     * Adds a {@code Command} to {@code CommandDeque}, given that the {@code Command} instance is not inside
     * {@code CommandDeque} already.
     *
     * @param command {@code Command} to be added.
     * @throws DuplicateCommandException If the {@code Command} to be added is already inside the
     * {@code CommandDeque}, where {@code CommandDeque#hasCommand(Command)} returns true.
     */
    public void addLatestCommand(Command command) throws DuplicateCommandException {
        requireNonNull(command);

        if (hasCommand(command)) {
            throw new DuplicateCommandException();
        }

        commands.addFirst(command);
        commandTracker.add(command);
        trimSize();
    }

    /**
     * Deletes the latest {@code Command} in {@code CommandDeque}.
     *
     * @return Latest {@code Command} in {@code CommandDeque}
     * @throws CommandNotFoundException if {@code CommandDeque} is empty.
     */
    public Command deleteLatestCommand() throws CommandNotFoundException {
        if (isEmpty()) {
            throw new CommandNotFoundException();
        }
        Command command = commands.removeFirst();
        commandTracker.remove(command);
        return command;
    }

    /**
     * Deletes the oldest {@code Command} in {@code CommandDeque}.
     *
     * @return Oldest {@code Command} in {@code CommandDeque}.
     * @throws CommandNotFoundException If {@code CommandDeque} is empty.
     */
    public Command deleteOldestCommand() throws CommandNotFoundException {
        if (isEmpty()) {
            throw new CommandNotFoundException();
        }
        Command command = commands.removeLast();
        commandTracker.remove(command);
        return command;
    }

    /**
     * Clears all commands stored in {@code CommandDeque}.
     */
    public void clearCache() {
        commands.clear();
        commandTracker.clear();
    }

    /**
     * Removes commands until the size limit is not exceeded.
     * Commands are deleted starting from the oldest commands.
     */
    private void trimSize() {
        while (commands.size() > sizeLimit) {
            Command command = commands.removeLast();
            commandTracker.remove(command);
        }
    }
}

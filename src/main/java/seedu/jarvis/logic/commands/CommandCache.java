package seedu.jarvis.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

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
public class CommandCache {
    /** Starting default limit to be assigned to each instance. */
    private static final int DEFAULT_LIMIT = 10;
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
    private int limit;

    /**
     * Creates a new {@code CommandCache} with the default size limit of DEFAULT_LIMIT.
     * This size limit can be set to another number.
     */
    public CommandCache() {
        commands = new ArrayDeque<>();
        commandTracker = new HashSet<>();
        limit = DEFAULT_LIMIT;
    }

    /**
     * Number of commands stored in {@code CommandCache}.
     * @return The number of commands stored in {@code CommandCache}.
     */
    public int getSize() {
        assert commands.size() == commandTracker.size() : "command disparity between deque and set.";
        return commands.size();
    }

    /**
     * Gets the current command size limit of the {@code CommandCache}.
     * @return current command size limit of {@code CommandCache}.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Updates the deque size limit
     * Trims the deque to the new size if the new size limit is lower that the original.
     * Returns null if the limit is invalid, which is lesser than zero.
     *
     * @param limit The new size limit.
     */
    public void setLimit(int limit) {
        if (limit < 0) {
            return;
        }
        this.limit = limit;
        trimSize();
    }

    /**
     * Returns whether there are any commands stored in {@code CommandCache}.
     * @return Whether the {@code CommandCache} is storing any commands.
     */
    public boolean isEmpty() {
        return commands.isEmpty() && commandTracker.isEmpty();
    }

    /**
     * Checks if this command object instance is already stored inside the {@code CommandCache}.
     *
     * @param command Command object instance to be checked.
     * @return Whether the command is stored inside the {@code CommandCache}.
     */
    public boolean hasCommand(Command command) {
        return !commands.isEmpty() && commandTracker.contains(command);
    }

    /**
     * Gets the most recent {@code Command} added to {@code CommandCache} which is at the front of the deque.
     * Returns null if the {@code CommandCache} is empty.
     *
     * @return The most recent {@code Command} added to {@code CommandCache}, returns null if
     * {@code CommandCache#isEmpty()} is true.
     */
    public Command getLatestCommand() {
        if (isEmpty()) {
            return null;
        }
        return commands.getFirst();
    }

    /**
     * Adds a {@code Command} to {@code CommandCache}, given that the {@code Command} instance is not inside
     * {@code CommandCache} already.
     *
     * @param command {@code Command} to be added.
     * @throws DuplicateCommandException If the {@code Command} to be added is already inside the
     * {@code CommandCache}, where {@code CommandCache#hasCommand(Command)} returns true.
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
     * Deletes the oldest {@code Command} in {@code CommandCache}.
     * If there are no commands, return null.
     *
     * @return Oldest {@code Command} in {@code CommandCache}, null if {@code CommandCache#isEmpty()} returns true.
     */
    public Command deleteOldestCommand() {
        if (isEmpty()) {
            return null;
        }
        Command command = commands.removeLast();
        commandTracker.remove(command);
        return command;
    }

    /**
     * Removes commands until the size limit is not exceeded.
     * Commands are deleted starting from the oldest commands.
     */
    private void trimSize() {
        while (commands.size() > limit) {
            Command command = commands.removeLast();
            commandTracker.remove(command);
        }
    }
}

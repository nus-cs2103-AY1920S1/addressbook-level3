package seedu.jarvis.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;

/**
 * A deque of commands that does not allow nulls.
 *
 * The back of the deque stores the latest commands, while the front of the deque stores the oldest commands.
 * The deque is kept within the size given by the value of limit, if the deque exceeds the
 * size, it will remove commands till it is within the size limit, removing the oldest commands first.
 */
public class CommandDeque {
    /** Size limit of the maximum possible number of commands to store. */
    private static final int SIZE_LIMIT = 10;
    /**
     * Deque to store commands. Deque is used to facilitate adding new commands to the back of the deque, and deleting
     * old commands from the front of the deque.
     */
    private Deque<Command> commands;

    /**
     * Creates a new {@code CommandDeque} with the size limit of DEFAULT_INITIAL_SIZE_LIMIT.
     */
    public CommandDeque() {
        commands = new ArrayDeque<>();
    }

    /**
     * Creates a new {@code CommandDeque} with the same {@code Command} objects from the {@code CommandDeque} object
     * given as argument.
     *
     * @param commandDeque {@code CommandDeque}
     */
    public CommandDeque(CommandDeque commandDeque) {
        this();
        resetData(commandDeque);
    }

    /**
     * Gets a {@code List} of all commands being stored in the {@code CommandDeque}.
     * The commands in the list are ordered chronologically to when they were inserted into the {@code CommandDeque},
     * with the latest command being at the back of the list and the oldest command being at the front of the list.
     *
     * @return {@code List} of all the commands being stored in the {@code CommandDeque}.
     */
    public List<Command> getCommands() {
        return new ArrayList<>(commands);
    }

    /**
     * Gets the size limit of {@code CommandDeque}.
     *
     * @return The size limit of {@code CommandDeque}.
     */
    public static int getSizeLimit() {
        return SIZE_LIMIT;
    }

    /**
     * Gets the number of commands stored in {@code CommandDeque}.
     *
     * @return The number of commands stored in {@code CommandDeque}.
     */
    public int getSize() {
        return commands.size();
    }

    /**
     * Returns whether there are any commands stored in {@code CommandDeque}.
     *
     * @return Whether the {@code CommandDeque} is storing any commands.
     */
    public boolean isEmpty() {
        return commands.isEmpty();
    }

    /**
     * Adds the commands from {@code CommandDeque} argument to this {@code CommandDeque}.
     * Null is not a valid argument.
     *
     * @param commandDeque {@code CommandDeque} object's commands to be added to this {@code CommandDeque}.
     */
    public void resetData(CommandDeque commandDeque) {
        requireNonNull(commandDeque);
        commandDeque.getCommands().forEach(commands::addLast);
    }

    /**
     * Gets the most recent {@code Command} added to {@code CommandDeque} which is at the back of the {@code Deque}.
     * Throws a {@code CommandNotFoundException} which extends from {@code RuntimeException} if the {@code CommandDeque}
     * is empty before getting the latest {@code Command}.
     *
     * @return The most recent {@code Command} added to {@code CommandDeque}.
     */
    public Command getLatestCommand() {
        if (isEmpty()) {
            throw new CommandNotFoundException();
        }
        return commands.getLast();
    }

    /**
     * Adds a {@code Command} to {@code CommandDeque}, which cannot be null.
     * After adding {@code Command}, {@code CommandDeque} will trim its size to adhere to its size limit
     * by calling {@code CommandDeque#trimSize()}.
     *
     * @param command {@code Command} to be added.
     */
    public void addLatestCommand(Command command) {
        requireNonNull(command);
        commands.addLast(command);
        trimSize();
    }

    /**
     * Deletes the latest {@code Command} in {@code CommandDeque} which is at the back of the {@code Deque}.
     * Throws a {@code CommandNotFoundException} which extends from {@code RuntimeException} if the {@code CommandDeque}
     * is empty before deleting the latest {@code Command}.
     *
     * @return Latest {@code Command} in {@code CommandDeque}.
     *
     */
    public Command deleteLatestCommand() {
        if (isEmpty()) {
            throw new CommandNotFoundException(); // RuntimeException
        }
        return commands.removeLast();
    }

    /**
     * Deletes the oldest {@code Command} in {@code CommandDeque} which is at the front of the {@code Deque}.
     * Throws a {@code CommandNotFoundException} which extends from {@code RuntimeException} if the {@code CommandDeque}
     * is empty before deleting the oldest {@code Command}.
     *
     * @return Oldest {@code Command} in {@code CommandDeque}.
     */
    public Command deleteOldestCommand() {
        if (isEmpty()) {
            throw new CommandNotFoundException();
        }
        return commands.removeFirst();
    }

    /**
     * Clears all commands stored in {@code CommandDeque}.
     */
    public void clear() {
        commands.clear();
    }

    /**
     * Removes commands until the size limit is not exceeded.
     * Commands are deleted starting from the oldest commands.
     */
    private void trimSize() {
        while (commands.size() > SIZE_LIMIT) {
            deleteOldestCommand();
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if it is the same object.
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls.
        if (!(obj instanceof CommandDeque)) {
            return false;
        }

        // checks that each command in the deque are equal to each other taking into account relative positioning.
        return getCommands().equals(((CommandDeque) obj).getCommands());
    }
}

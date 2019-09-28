package seedu.jarvis.logic.version;

import static seedu.jarvis.logic.commands.CommandDeque.DEFAULT_INITIAL_SIZE_LIMIT;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandDeque;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;
import seedu.jarvis.model.Model;

/**
 * VersionControl manages the invertible commands that has been executed and inversely executed.
 * VersionControl follows a Singleton pattern, ensuring that there is one instance in the whole program.
 * VersionControl is thread safe by using Enum to keep track of a single instance.
 * VersionControl has eager initialisation of instance due to using Enum to keep track of instance.
 */
public enum VersionControl {
    /** Enum constant to ensure single instance. */
    INSTANCE;

    /** Maximum number of executed commands to be remembered for undo / redo functionality. */
    private int versionLimit;
    /** Store executed commands that can be undone. */
    private CommandDeque executedCommands;
    /** Store inversely executed commands that can be redone. */
    private CommandDeque inverselyExecutedCommands;

    /**
     * Constructs the instance of VersionControl, setting the version limit to default size limit.
     * {@code executedCommands} holds the invertible commands that have been executed.
     * {@code inverselyExecutedCommands} holds the invertible commands that have been inversely executed.
     */
    VersionControl() {
        // setting version limit to the default limit, which can be updated.
        versionLimit = DEFAULT_INITIAL_SIZE_LIMIT;
        // manually construct with default limit for clarity.
        executedCommands = new CommandDeque(versionLimit);
        // no size limit for undone commands.
        inverselyExecutedCommands = new CommandDeque(Integer.MAX_VALUE);
    }

    /**
     * Gets the current version limit.
     * @return current version limit.
     */
    public int getVersionLimit() {
        return versionLimit;
    }

    /**
     * Update {@code versionLimit} to a update value.
     * @param versionLimit Updated {@code versionLimit} value.
     */
    public void setVersionLimit(int versionLimit) {
        this.versionLimit = versionLimit;
        executedCommands.setSizeLimit(versionLimit);
    }

    /**
     * Changes the {@code versionLimit} to its default value.
     */
    public void resetVersionLimit() {
        this.versionLimit = DEFAULT_INITIAL_SIZE_LIMIT;
        executedCommands.setSizeLimit(versionLimit);
    }

    /**
     * Gets the number of available undoable commands in {@code executedCommands}.
     * @return The number of undoable commands.
     */
    public int getTotalNumberOfUndoableCommands() {
        return executedCommands.getSize();
    }

    /**
     * Sets the singleton instance to its initial state.
     */
    public void hardReset() {
        versionLimit = DEFAULT_INITIAL_SIZE_LIMIT;
        executedCommands = new CommandDeque(versionLimit);
        inverselyExecutedCommands = new CommandDeque(Integer.MAX_VALUE);
    }

    /**
     * Adds an invertible {@code Command} to be managed by {@code VersionControl}.
     * {@code Command} is added to {@code executedCommands}.
     *
     * @param command {@code Command} to be added.
     * @throws CommandNotInvertibleException If the {@code Command} instance is not invertible.
     * @throws DuplicateCommandException If the {@code Command} instance is already inside {@code VersionControl}.
     */
    public void addExecutedCommand(Command command) throws CommandNotInvertibleException, DuplicateCommandException {

        if (!command.hasInverseExecution()) {
            throw new CommandNotInvertibleException();
        }

        if (inverselyExecutedCommands.hasCommand(command)) {
            throw new DuplicateCommandException();
        }

        executedCommands.addLatestCommand(command);
    }

    /**
     * Adds an invertible {@code Command} to be managed by {@code VersionControl}.
     * {@code Command} is added to {@code inverselyExecutedCommands}.
     *
     * @param command {@code Command} to be added.
     * @throws CommandNotInvertibleException If the {@code Command} instance is not invertible.
     * @throws DuplicateCommandException If the {@code Command} instance is already inside {@code VersionControl}.
     */
    public void addInverselyExecutedCommand(Command command) throws CommandNotInvertibleException,
            DuplicateCommandException {

        if (!command.hasInverseExecution()) {
            throw new CommandNotInvertibleException();
        }

        if (executedCommands.hasCommand(command)) {
            throw new DuplicateCommandException();
        }

        inverselyExecutedCommands.addLatestCommand(command);
    }

    /**
     * Undo the execution of the latest {@code Command} in {@code executedCommands} to the given {@code Model}.
     * Stores the undone {@code Command} in {@code inverselyExecutedCommands}.
     *
     * @param model {@code Model} for the command to inversely execute on.
     * @return {@code CommandResult} from the inverse execution of the command.
     * @throws CommandNotFoundException If {@code executedCommands} is empty.
     * @throws CommandException If the inverse execution of the command throws a {@code CommandException}.
     * @throws DuplicateCommandException If {@code Command} is already stored in {@code VersionControl}.
     */
    public CommandResult undo(Model model) throws CommandNotFoundException, CommandException,
            DuplicateCommandException {
        Command command = executedCommands.deleteLatestCommand();
        CommandResult commandResult = command.executeInverse(model);
        inverselyExecutedCommands.addLatestCommand(command);
        return commandResult;
    }

    /**
     * Undo the inverse execution of the latest {@code Command} in {@code inverselyExecutedCommands} to the given
     * {@code Model}.
     * Stores the redone {@code Command} in {@code executedCommands}.
     *
     * @param model {@code Model} for the command to be execute on.
     * @return {@code CommandResult} from the execution of the command.
     * @throws CommandNotFoundException If {@code inverselyExecutedCommands} is empty.
     * @throws CommandException If the execution of the command throws a {@code CommandException}.
     * @throws DuplicateCommandException If {@code Command} is already stored in {@code VersionControl}.
     */
    public CommandResult redo(Model model) throws CommandNotFoundException, CommandException,
            DuplicateCommandException {
        Command command = inverselyExecutedCommands.deleteLatestCommand();
        CommandResult commandResult = command.execute(model);
        executedCommands.addLatestCommand(command);
        return commandResult;
    }
}

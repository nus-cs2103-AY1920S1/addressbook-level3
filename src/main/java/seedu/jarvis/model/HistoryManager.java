package seedu.jarvis.model;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandDeque;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotInvertibleException;

/**
 * Wraps all data at the HistoryManager level.
 * Keeps track of the history of invertible commands applied in the application and stores them to facilitate undo and
 * redo operations by executing and inversely executing these commands.
 */
public class HistoryManager {
    /** Stores all executed commands that can be undone. */
    private CommandDeque executedCommands;
    /** Stores all inversely executed commands that can be redone. */
    private CommandDeque inverselyExecutedCommands;

    /**
     * Constructs an empty HistoryManager to store commands.
     */
    public HistoryManager() {
        executedCommands = new CommandDeque();
        inverselyExecutedCommands = new CommandDeque();
    }

    /**
     * Constructs a HistoryManager with reference from another HistoryManager,
     * adding its commands to this HistoryManager.
     *
     * @param historyManager {@code HistoryManager} whose data this {@code HistoryManager} is taking reference from.
     */
    public HistoryManager(HistoryManager historyManager) {
        this();
        resetData(historyManager);
    }

    /**
     * Gets the maximum number of commands that {@code HistoryManager} can store in {@code executedCommands} and
     * {@code inverselyExecutedCommands} each.
     *
     * @return Maximum number of commands that {@code HistoryManager} can store in {@code executedCommands} and
     * {@code inverselyExecutedCommands} each.
     */
    public static int getHistoryRange() {
        return CommandDeque.getSizeLimit();
    }

    /**
     * Gets the {@code CommandDeque} of executed commands.
     *
     * @return {@code CommandDeque} of executed commands.
     */
    public CommandDeque getExecutedCommands() {
        return executedCommands;
    }

    /**
     * Gets the {@code CommandDeque} of inversely executed commands.
     *
     * @return {@code CommandDeque} of inversely executed commands.
     */
    public CommandDeque getInverselyExecutedCommands() {
        return inverselyExecutedCommands;
    }

    /**
     * Gets the number of available executed commands.
     *
     * @return The number of available executed commands.
     */
    public int getNumberOfAvailableExecutedCommands() {
        return executedCommands.getSize();
    }

    /**
     * Gets the number of available inversely executed commands.
     *
     * @return The number of available inversely executed commands.
     */
    public int getNumberOfAvailableInverselyExecutedCommands() {
        return inverselyExecutedCommands.getSize();
    }

    /**
     * Checks Whether {@code HistoryManager} has executed commands to inversely execute.
     *
     * @return Whether {@code HistoryManager} has executed commands to inversely execute.
     */
    public boolean canRollback() {
        return !executedCommands.isEmpty();
    }

    /**
     * Checks Whether {@code HistoryManager} has inversely executed commands to execute.
     *
     * @return Whether {@code HistoryManager} has inversely executed commands to execute.
     */
    public boolean canCommit() {
        return !inverselyExecutedCommands.isEmpty();
    }

    /**
     * Resets all commands in {@code executedCommands} and {@code inverselyExecutedCommands} to the commands in the
     * given {@code HistoryManager}.
     *
     * @param historyManager {@code HistoryManager} to take reference from.
     */
    public void resetData(HistoryManager historyManager) {
        requireNonNull(historyManager);
        this.executedCommands = new CommandDeque(historyManager.getExecutedCommands());
        this.inverselyExecutedCommands = new CommandDeque(historyManager.getInverselyExecutedCommands());
    }

    /**
     * Adds a {@code Command} to be stored as the latest executed command.
     *
     * @param command {@code Command} to be added.
     * @throws CommandNotInvertibleException If the command to be added is not invertible.
     */
    public void rememberExecutedCommand(Command command) throws CommandNotInvertibleException {
        if (!command.hasInverseExecution()) {
            throw new CommandNotInvertibleException();
        }
        executedCommands.addLatestCommand(command);
    }

    /**
     * Adds a {@code Command} to be stored as the latest inversely executed command.
     *
     * @param command {@code Command} to be added.
     * @throws CommandNotInvertibleException If the command to be added is not invertible.
     */
    public void rememberInverselyExecutedCommand(Command command) throws CommandNotInvertibleException {
        if (!command.hasInverseExecution()) {
            throw new CommandNotInvertibleException();
        }
        inverselyExecutedCommands.addLatestCommand(command);
    }

    /**
     * Deletes all {@code Command} objects stored as {@code inverselyExecutedCommands}.
     */
    public void forgetAllInverselyExecutedCommands() {
        inverselyExecutedCommands.clear();
    }

    /**
     * Inversely executes the latest executed {@code Command} onto the {@code Model}. This rolls back the changes made
     * from the execution of the latest executed {@code Command} made to the {@code Model}.
     *
     * @param model {@code Model} for the {@code Command} to be inversely executed on.
     * @return Whether the rollback was successful.
     */
    public boolean rollback(Model model) {
        Command command = executedCommands.getLatestCommand();
        try {
            command.executeInverse(model);
        } catch (CommandException ce) {
            return false;
        }
        inverselyExecutedCommands.addLatestCommand(executedCommands.deleteLatestCommand());
        return true;
    }

    /**
     * Executes the latest inversely executed {@code Command} onto the {@code Model}. This rolls back back the changes
     * made from the inverse execution of the latest inversely executed {@code Command} made to the {@code Model}.
     *
     * @param model {@code Model} for the {@code Command} to be executed on.
     * @return Whether the commit was successful.
     */
    public boolean commit(Model model) {
        Command command = inverselyExecutedCommands.getLatestCommand();
        try {
            command.execute(model);
        } catch (CommandException ce) {
            return false;
        }
        executedCommands.addLatestCommand(inverselyExecutedCommands.deleteLatestCommand());
        return true;
    }

    /**
     * Checks for equality with other objects. If object is a HistoryManager, and has the same commands in
     * {@code executedCommands} and {@code inverselyExecutedCommands}, in the same order.
     *
     * @param obj {@code Object} to compare to.
     * @return Whether this {@code Object} is equal to this {@code HistoryManager}.
     */
    @Override
    public boolean equals(Object obj) {
        // short circuit if it is the same object.
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls.
        if (!(obj instanceof HistoryManager)) {
            return false;
        }

        // checks that for equality for executedCommands and inverselyExecutedCommands.
        HistoryManager other = (HistoryManager) obj;
        return executedCommands.equals(other.executedCommands)
                && inverselyExecutedCommands.equals(other.inverselyExecutedCommands);
    }
}

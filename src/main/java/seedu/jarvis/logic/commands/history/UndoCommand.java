package seedu.jarvis.logic.commands.history;

import static java.util.Objects.requireNonNull;

import java.util.stream.IntStream;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Undo user actions done to the application.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": undo actions. "
            + "Parameters: " + "UNDO_NUMBER (Must be a Positive Number, "
            + "Example: " + COMMAND_WORD
            + ", " + COMMAND_WORD + " " + "5";


    public static final String MESSAGE_SUCCESS = "Undone %1$d command(s)";
    public static final String MESSAGE_NOTHING_TO_UNDO = "Nothing available to undo.";
    public static final String MESSAGE_TOO_MANY_UNDO = "There is a maximum of %d commands that can be undone\n"
            + "There are only %d command(s) available to be undone";
    public static final String MESSAGE_UNABLE_TO_UNDO =
            "Unable to undo %d command(s), there was a problem with undoing command %d";
    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be undone";

    public static final boolean HAS_INVERSE = false;

    /** Number of commands to undo. */
    private int numberOfTimes;

    /**
     * Assigns the number of commands to be inversely executed by this undo command.
     *
     * @param numberOfTimes Number of commands to be inversely executed.
     */
    public UndoCommand(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    /**
     * Assigns {@code numberOfTimes} to one. This undo command will undo a single command.
     */
    public UndoCommand() {
        this(1);
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Undo {@code numberOfTimes} number of commands onto the {@code Model}.
     * If any of the commands that are being undone fails, all prior changes made to {@code Model} will be committed
     * back to the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of the number of commands inversely executed from the undo.
     * @throws CommandException If there are no commands available to undo, or if the {@code numberOfTimes} is more than
     * the available number of commands available to be inversely executed, or if there was an unsuccessful undone
     * commands during this command's execution, or if there was an unsuccessful commit from an unsuccessful undone
     * command.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        assert numberOfTimes > 0 : "numberOfTimes should not be less than 1";

        if (!model.canRollback()) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }

        if (numberOfTimes > model.getAvailableNumberOfExecutedCommands()) {
            throw new CommandException(String.format(MESSAGE_TOO_MANY_UNDO, model.getHistoryRange(),
                    model.getAvailableNumberOfExecutedCommands()));
        }

        int numberOfRollbacks = rollbackCommands(model);

        if (numberOfRollbacks < numberOfTimes) {
            abortRollbacks(numberOfRollbacks, model);
            throw new CommandException(String.format(MESSAGE_UNABLE_TO_UNDO, numberOfTimes, numberOfRollbacks + 1));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfRollbacks), true);
    }

    /**
     * Tries to roll back {@code numberOfTimes} number of commands on the given {@code Model}, by inversely executing
     * the commands.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The number of successful rollbacks made.
     */
    private int rollbackCommands(Model model) {
        requireNonNull(model);

        int numberOfRollbacks = 0;
        while (numberOfRollbacks < numberOfTimes && model.rollback()) {
            ++numberOfRollbacks;
        }
        return numberOfRollbacks;
    }

    /**
     * Reapplies {@code numberOfRollbacks} commands that were undone to the given {@code Model}. This is to abort the
     * changes made if {@code rollbackCommands(Model)} did not successfully rollback all the commands it was supposed
     * to.
     *
     * @param numberOfRollbacks Number of commands to redo on the given {@code Model}.
     * @param model {@code Model} which the command should operate on.
     */
    private void abortRollbacks(int numberOfRollbacks, Model model) {
        IntStream.range(0, numberOfRollbacks).forEach(index -> model.commit());
    }

    /**
     * There is no available inverse execution available, always throws a {@code CommandException}.
     * This is so that the {@code HistoryManager} does not remember an undo command.
     * The inverse of the command is represented by the redo command.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @throws CommandException Always thrown.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        throw new InvalidCommandToJsonException();
    }

    /**
     * Checks for equality of {@code numberOfTimes} if {@code other} is a {@code UndoCommand}.
     *
     * @param other {@code Object} to compare with.
     * @return Whether {@code other} is a {@code UndoCommand} with the same {@code numberOfTimes}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && ((UndoCommand) other).numberOfTimes == numberOfTimes);
    }
}

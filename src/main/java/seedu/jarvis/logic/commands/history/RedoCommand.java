package seedu.jarvis.logic.commands.history;

import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;

import java.util.stream.IntStream;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;

/**
 * Redo the user actions that was undone to the application.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": redo actions. "
            + "Parameters: " + PREFIX_UNDO_REDO + "REDO (Must be a Positive Number or \"all\" [case insensitive], "
            + "invalid numbers will set REDO to default value of 1) "
            + "Example: " + COMMAND_WORD
            + ", " + COMMAND_WORD + " " + PREFIX_UNDO_REDO + "all"
            + ", " + COMMAND_WORD + " " + PREFIX_UNDO_REDO + "5";

    public static final String MESSAGE_SUCCESS = "Redone %1$d commands";
    public static final String MESSAGE_NOTHING_TO_REDO = "Nothing available to redo.";
    public static final String MESSAGE_TOO_MANY_REDO = "There are only %1$d commands available to be redone";
    public static final String MESSAGE_UNABLE_TO_REDO =
            "Unable to redo %d command(s), there was a problem with redoing command %d";
    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be redone";

    public static final boolean HAS_INVERSE = false;

    /**
     * Number of commands to undo.
     */
    private int numberOfTimes;

    /**
     * Assigns the number of commands to be executed by this redo command.
     *
     * @param numberOfTimes Number of commands to be executed.
     */
    public RedoCommand(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    /**
     * Assigns {@code numberOfTimes} to one. This redo command will redo a single command.
     */
    public RedoCommand() {
        this(1);
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
     * Redo {@code numberOfTimes} number of commands onto the {@code Model}.
     * If any of the commands that are being redone fails, all prior changes made to {@code Model} will be rolled back.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of the number of commands executed from the redo.
     * @throws CommandException If there are no commands available to redo, or if the {@code numberOfTimes} is more than
     * the available number of commands available to be executed, or if there was an unsuccessful redone commands during
     * this command's execution, or if there was an unsuccessful rollback from an unsuccessful redone command.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canCommit()) {
            throw new CommandException(MESSAGE_NOTHING_TO_REDO);
        }

        if (numberOfTimes > model.getAvailableNumberOfInverselyExecutedCommands()) {
            throw new CommandException(String.format(MESSAGE_TOO_MANY_REDO,
                    model.getAvailableNumberOfInverselyExecutedCommands()));
        }

        int numberOfCommits = commitCommands(model);

        if (numberOfCommits < numberOfTimes) {
            abortCommits(numberOfCommits, model);
            throw new CommandException(String.format(MESSAGE_UNABLE_TO_REDO, numberOfTimes, numberOfCommits + 1));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfCommits));
    }

    /**
     * Tries to commit {@code numberOfTimes} number of commands on the given {@code Model}, by executing the commands.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The number of successful commits made.
     */
    private int commitCommands(Model model) {
        int numberOfCommits = 0;
        while (numberOfCommits < numberOfTimes && model.commit()) {
            ++numberOfCommits;
        }
        return numberOfCommits;
    }

    /**
     * Reapplies {@code numberOfCommits} commands that were redone to the given {@code Model}. This is to abort the
     * changes made if {@code commitCommands(Model)} did not successfully commit all the commands it was supposed
     * to.
     *
     * @param numberOfCommits Number of commands to undo on the given {@code Model}.
     * @param model {@code Model} which the command should operate on.
     */
    private void abortCommits(int numberOfCommits, Model model) {
        IntStream.range(0, numberOfCommits).forEach(index -> model.rollback());
    }


    /**
     * There is no available inverse execution available, always throws a {@code CommandException}.
     * This is so that the {@code HistoryManager} does not remember a redo command.
     * The inverse of the command is represented by the undo command.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @throws CommandException Always thrown.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    /**
     * Checks for equality of {@code numberOfTimes} if {@code other} is a {@code RedoCommand}.
     *
     * @param other {@code Object} to compare with.
     * @return Whether {@code other} is a {@code RedoCommand} with the same {@code numberOfTimes}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && ((RedoCommand) other).numberOfTimes == numberOfTimes);
    }

}


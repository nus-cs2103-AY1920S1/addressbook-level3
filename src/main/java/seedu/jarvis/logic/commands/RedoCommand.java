package seedu.jarvis.logic.commands;

import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;
import seedu.jarvis.logic.version.VersionControl;
import seedu.jarvis.model.Model;

/**
 * Redo the latest user action that was undone to the application.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": redo actions. "
            + "Parameters: " + PREFIX_UNDO_REDO + "REDO (Must be a Positive Number or \"all\" [case insensitive], "
            + "invalid numbers will set REDO to default value of 1) "
            + "Example: " + COMMAND_WORD
            + ", " + COMMAND_WORD + " " + PREFIX_UNDO_REDO + "all"
            + ", " + COMMAND_WORD + " " + PREFIX_UNDO_REDO + "5";

    public static final String MESSAGE_SUCCESS = "redone %1$d commands";

    public static final String MESSAGE_NO_COMMAND_TO_REDO = "There is no commands available to be redone";
    public static final String MESSAGE_DUPLICATE_COMMAND_ERROR = "There has been an error in redoing this command";
    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be redone";

    public static final boolean HAS_INVERSE = false;

    /**
     * Number of commands to undo.
     */
    private int numberOfTimes;

    /**
     * Assigns {@code numberOfTimes} to one. This redo command will redo a single command.
     */
    public RedoCommand() {
        this.numberOfTimes = 1;
    }

    /**
     * Assigns the number of commands to be executed by this redo command.
     * If the number is less than one, it is set to one.
     *
     * @param numberOfTimes Number of commands to be executed.
     */
    public RedoCommand(int numberOfTimes) {
        this.numberOfTimes = Math.max(numberOfTimes, 1);
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
     * Redo {@code numberOfTimes} number of commands.
     * These commands are executed onto the {@code Model} given.
     * If {@code numberOfTimes} is larger than the number of commands available to be redone, no exception is thrown,
     * the execution will just redo all the available commands unless there are no commands that can be redone at all.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of all the messages of the commands that were redone successfully, and how many
     * commands that were redone.
     * @throws CommandException If there were no commands that could be redone or if the operation resulted in a
     * {@code DuplicateCommandException}, which means that the operation resulted in an instance of a command
     * existing more than once in {@code VersionControl}.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;

        while (counter < numberOfTimes) {
            try {
                CommandResult commandResult = VersionControl.INSTANCE.redo(model);
                stringBuilder.append(commandResult.getFeedbackToUser()).append("\n");
            } catch (CommandNotFoundException cnfe) {
                break;
            } catch (DuplicateCommandException dce) {
                throw new CommandException(MESSAGE_DUPLICATE_COMMAND_ERROR);
            }
            ++counter;
        }

        if (counter == 0) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_REDO);
        }

        stringBuilder.append(String.format(MESSAGE_SUCCESS, counter));
        return new CommandResult(stringBuilder.toString());
    }

    /**
     * There is no available inverse execution available, always throws a {@code CommandException}.
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


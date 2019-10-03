package seedu.jarvis.logic.commands;

import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.exceptions.CommandNotFoundException;
import seedu.jarvis.logic.commands.exceptions.DuplicateCommandException;
import seedu.jarvis.logic.version.VersionControl;
import seedu.jarvis.model.Model;

/**
 * Undo the latest user action done to the application.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": undo actions. "
            + "Parameters: " + PREFIX_UNDO_REDO + "UNDO (Must be a Positive Number or \"all\" [case insensitive], "
            + "invalid numbers will set UNDO to default value of 1) "
            + "Example: " + COMMAND_WORD
            + ", " + COMMAND_WORD + " " + PREFIX_UNDO_REDO + "all"
            + ", " + COMMAND_WORD + " " + PREFIX_UNDO_REDO + "5";


    public static final String MESSAGE_SUCCESS = "undone %1$d commands";

    public static final String MESSAGE_NO_COMMAND_TO_UNDO = "There is no commands available to be undone";
    public static final String MESSAGE_DUPLICATE_COMMAND_ERROR = "There has been an error in undoing this command";
    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be undone";

    public static final boolean HAS_INVERSE = false;

    /** Number of commands to undo. */
    private int numberOfTimes;

    /**
     * Assigns {@code numberOfTimes} to one. This undo command will undo a single command.
     */
    public UndoCommand() {
        numberOfTimes = 1;
    }

    /**
     * Assigns the number of commands to be inversely executed by this undo command.
     * If the number is less than one, it is set to one.
     *
     * @param numberOfTimes Number of commands to be inversely executed.
     */
    public UndoCommand(int numberOfTimes) {
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
     * Undo {@code numberOfTimes} number of commands.
     * These commands are inversely executed onto the {@code Model} given.
     * If {@code numberOfTimes} is larger than the number of commands available to be undone, no exception is thrown,
     * the execution will just undo all the available commands unless there are no commands that can be undone at all.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of all the messages of the commands that were undone successfully, and how many
     * commands that were undone.
     * @throws CommandException If there were no commands that could be undone or if the operation resulted in a
     * {@code DuplicateCommandException}, which means that the operation resulted in an instance of a command
     * existing more than once in {@code VersionControl}.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;

        while (counter < numberOfTimes) {
            try {
                CommandResult commandResult = VersionControl.INSTANCE.undo(model);
                stringBuilder.append(commandResult.getFeedbackToUser()).append("\n");
            } catch (CommandNotFoundException cnfe) {
                break;
            } catch (DuplicateCommandException dce) {
                throw new CommandException(MESSAGE_DUPLICATE_COMMAND_ERROR);
            }
            ++counter;
        }

        if (counter == 0) {
            throw new CommandException(MESSAGE_NO_COMMAND_TO_UNDO);
        }


        stringBuilder.append(String.format(MESSAGE_SUCCESS, counter));
        return new CommandResult(stringBuilder.toString());
    }

    /**
     * There is no available inverse execution available, always throws a {@code CommandException}.
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
     * Checks for equality of {@code numberOfTimes} if {@code other} is a {@code UndoCommand}.
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

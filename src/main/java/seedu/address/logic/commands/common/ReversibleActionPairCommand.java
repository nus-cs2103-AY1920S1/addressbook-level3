package seedu.address.logic.commands.common;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a reversible command with hidden internal logic and the ability to be executed.
 */
public class ReversibleActionPairCommand implements Command {

    public static final String MESSAGE_UNDO_SUCCESS = "Undo successful! %1$s";
    public static final String MESSAGE_REDO_SUCCESS = "Redo successful! %1$s";

    private final ReversibleCommand primaryCommand;
    private final ReversibleCommand reverseCommand;

    /**
     * Represents the execution phase of the command.
     */
    private enum ExecutionPhase {
        FIRSTEXECUTION,
        EXECUTED,
        UNDONE
    }
    private ExecutionPhase executionPhase;

    public ReversibleActionPairCommand(ReversibleCommand primaryCommand, ReversibleCommand reverseCommand) {
        requireAllNonNull(primaryCommand, reverseCommand);
        this.primaryCommand = primaryCommand;
        this.reverseCommand = reverseCommand;
        this.executionPhase = ExecutionPhase.FIRSTEXECUTION;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (executionPhase != ExecutionPhase.FIRSTEXECUTION) {
            throw new CommandException("Command has already been executed.");
        }

        executionPhase = ExecutionPhase.EXECUTED;
        return primaryCommand.execute(model);
    }

    /**
     * Undoes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult undo(Model model) throws CommandException {
        if (executionPhase != ExecutionPhase.EXECUTED) {
            throw new CommandException(primaryCommand.getFailedUndoMessage());
        }

        executionPhase = ExecutionPhase.UNDONE;
        CommandResult reverseCommandResult = reverseCommand.execute(model);
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, reverseCommandResult.getFeedbackToUser()));
    }

    /**
     * Re-execute the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult redo(Model model) throws CommandException {
        if (executionPhase != ExecutionPhase.UNDONE) {
            throw new CommandException("Command has already been executed.");
        }

        executionPhase = ExecutionPhase.EXECUTED;
        CommandResult commandResult = primaryCommand.execute(model);
        return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, commandResult.getFeedbackToUser()));
    }
}

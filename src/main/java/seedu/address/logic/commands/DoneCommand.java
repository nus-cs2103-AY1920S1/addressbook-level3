package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exits the serve mode.
 */
public class DoneCommand extends Command implements ReversibleCommand {
    public static final String COMMAND_WORD = "done";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns to List Mode. \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Exited from Serve Mode. ";

    private final boolean isUndoRedo;

    private Command undoCommand;
    private Command redoCommand;

    /**
     * Creates a DoneCommand to exit serve mode.
     */
    public DoneCommand() {
        this.isUndoRedo = false;
    }

    /**
     * Creates a DoneCommand to exit serve mode.
     *
     * @param isUndoRedo used to check whether the DoneCommand is an undo/redo command.
     */
    public DoneCommand(boolean isUndoRedo) {
        this.isUndoRedo = isUndoRedo;
    }


    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.isServeMode()) {
            throw new CommandException(MESSAGE_NOT_IN_SERVE_MODE);
        }
        undoCommand = new ServeCommand(model.getServingBorrower().getBorrowerId(), true);
        redoCommand = new DoneCommand(true);
        model.exitsServeMode();
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
    }

    @Override
    public Command getUndoCommand() {
        return undoCommand;
    }

    @Override
    public Command getRedoCommand() {
        return redoCommand;
    }

    @Override
    public boolean isUndoRedoCommand() {
        return isUndoRedo;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this || other instanceof DoneCommand);
    }
}

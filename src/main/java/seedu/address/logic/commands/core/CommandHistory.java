package seedu.address.logic.commands.core;

import java.util.Stack;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Keeps track of the execution of {@code UndoableCommand} commands that alters one or more entries.
 */
public class CommandHistory {

    private final Stack<UndoableCommand> commandHistory = new Stack<>();

    /**
     * Checks if an undo operation is available.
     *
     * @return {@code True} if an undo operation is available, otherwise {@code False}.
     */
    public boolean canUndo() {
        return commandHistory.size() > 0;
    }

    /**
     * Adds an {@code UndoableCommand} to the command history.
     *
     * @param command to be added to the command history.
     */
    public void addToCommandHistory(UndoableCommand command) {
        commandHistory.add(command);
    }


    /**
     * Undoes the previous {@code UndoableCommand} command and returns the result message.
     * The command history cannot be empty.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult performUndo(Model model) throws CommandException {
        if (!canUndo()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return commandHistory.pop().undo(model);
    }

}

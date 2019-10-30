package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.events.Event;
import seedu.address.model.Model;

/**
 * An redo command to revert the changes made in the most recent undo command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Latest undo command successfully reverted!";
    public static final String MESSAGE_FAILURE = "No commands to redo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (CommandHistory.isEmptyRedoStack()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Event redoableEvent = CommandHistory.getRedoEvent();
        CommandHistory.addToUndoStack(redoableEvent); //redoable event added to undo stack
        UndoableCommand commandToUndo = redoableEvent.redo();
        return commandToUndo.execute(model);
    }
}

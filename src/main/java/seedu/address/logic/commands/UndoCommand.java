package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.events.Event;
import seedu.address.model.Model;

/**
 * An undo command to revert the changes made in the most recent command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Latest command successfully undone!";
    public static final String MESSAGE_FAILURE = "No commands to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (CommandHistory.isEmptyUndoStack()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Event undoableEvent = CommandHistory.getUndoEvent();
        CommandHistory.addToRedoStack(undoableEvent); //undoable event added to redo stack
        UndoableCommand commandToUndo = undoableEvent.undo();
        return commandToUndo.execute(model);
    }
}

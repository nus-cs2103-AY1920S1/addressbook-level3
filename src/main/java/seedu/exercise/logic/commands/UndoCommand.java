package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.history.Event;
import seedu.exercise.logic.commands.history.EventHistory;
import seedu.exercise.model.Model;

/**
 * Undoes the last executed command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the latest command called.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Command undone: \n%1$s";
    public static final String MESSAGE_EMPTY_UNDO_STACK = "There is no command to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EventHistory eventHistory = EventHistory.getInstance();

        if (eventHistory.isUndoStackEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_UNDO_STACK);
        }

        Event eventToUndo = eventHistory.undo(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToUndo));
    }

}

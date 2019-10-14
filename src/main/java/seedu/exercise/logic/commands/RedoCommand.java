package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.history.Event;
import seedu.exercise.logic.commands.history.EventHistory;
import seedu.exercise.model.Model;

/**
 * Undoes the last executed command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Action redone: \n%1$s";
    public static final String MESSAGE_EMPTY_REDO_STACK = "There is no command to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EventHistory eventHistory = EventHistory.getInstance();

        if (eventHistory.isRedoStackEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_REDO_STACK);
        }

        Event eventToRedo = eventHistory.redo(model);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, eventToRedo));
    }

}

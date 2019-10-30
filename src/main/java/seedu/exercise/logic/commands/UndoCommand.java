package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.events.AddExerciseEvent;
import seedu.exercise.logic.commands.events.AddRegimeEvent;
import seedu.exercise.logic.commands.events.ClearEvent;
import seedu.exercise.logic.commands.events.DeleteExerciseEvent;
import seedu.exercise.logic.commands.events.DeleteRegimeEvent;
import seedu.exercise.logic.commands.events.EditEvent;
import seedu.exercise.logic.commands.events.EditRegimeEvent;
import seedu.exercise.logic.commands.events.Event;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.ui.ListResourceType;

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
        model.updateStatistic();

        //TODO Refactor this out for v1.4!!!!!!!!!!!!!!!!!! I want to puke looking at this code.
        ListResourceType type = ListResourceType.NULL;
        if (eventToUndo instanceof AddExerciseEvent || eventToUndo instanceof DeleteExerciseEvent
                || eventToUndo instanceof EditEvent || eventToUndo instanceof ClearEvent) {
            type = ListResourceType.EXERCISE;
        } else if (eventToUndo instanceof AddRegimeEvent || eventToUndo instanceof DeleteRegimeEvent
                || eventToUndo instanceof EditRegimeEvent) {
            type = ListResourceType.REGIME;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToUndo), type);
    }
}

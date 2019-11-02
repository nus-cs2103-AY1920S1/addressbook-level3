package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.events.AddExerciseEvent;
import seedu.exercise.logic.commands.events.AddRegimeEvent;
import seedu.exercise.logic.commands.events.ClearEvent;
import seedu.exercise.logic.commands.events.DeleteExerciseEvent;
import seedu.exercise.logic.commands.events.DeleteRegimeEvent;
import seedu.exercise.logic.commands.events.EditExerciseEvent;
import seedu.exercise.logic.commands.events.EditRegimeEvent;
import seedu.exercise.logic.commands.events.Event;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.ResolveEvent;
import seedu.exercise.logic.commands.events.ScheduleCompleteEvent;
import seedu.exercise.logic.commands.events.ScheduleRegimeEvent;
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
        ListResourceType type = getListResourceType(eventToUndo);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToUndo), type);
    }

    /**
     * Returns the list resource type for display based on the type of event that has been undone.
     *
     * @param event the event that was undone
     * @return a list resource type enum object
     */
    private ListResourceType getListResourceType(Event event) {
        ListResourceType type = ListResourceType.NULL;
        if (event instanceof AddExerciseEvent
                || event instanceof DeleteExerciseEvent
                || event instanceof EditExerciseEvent
                || event instanceof ClearEvent) {
            type = ListResourceType.EXERCISE;
        } else if (event instanceof AddRegimeEvent
                || event instanceof DeleteRegimeEvent
                || event instanceof EditRegimeEvent) {
            type = ListResourceType.REGIME;
        } else if (event instanceof ScheduleCompleteEvent
                || event instanceof ScheduleRegimeEvent
                || event instanceof ResolveEvent) {
            type = ListResourceType.SCHEDULE;
        }
        return type;
    }
}

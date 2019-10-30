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
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Command redone: \n%1$s";
    public static final String MESSAGE_EMPTY_REDO_STACK = "There is no command to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EventHistory eventHistory = EventHistory.getInstance();

        if (eventHistory.isRedoStackEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_REDO_STACK);
        }

        Event eventToRedo = eventHistory.redo(model);
        model.updateStatistic();


        //TODO Refactor this out for v1.4!!!!!!!!!!!!!!!!!! I want to puke looking at this code.
        ListResourceType type = ListResourceType.NULL;
        if (eventToRedo instanceof AddExerciseEvent || eventToRedo instanceof DeleteExerciseEvent
                || eventToRedo instanceof EditEvent || eventToRedo instanceof ClearEvent) {
            type = ListResourceType.EXERCISE;
        } else if (eventToRedo instanceof AddRegimeEvent || eventToRedo instanceof DeleteRegimeEvent
                || eventToRedo instanceof EditRegimeEvent) {
            type = ListResourceType.REGIME;
        }

        return new CommandResult(
            String.format(MESSAGE_SUCCESS, eventToRedo), type);
    }
}

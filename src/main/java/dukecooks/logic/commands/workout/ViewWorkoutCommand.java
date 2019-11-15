package dukecooks.logic.commands.workout;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ViewCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.Workout;

/**
 * Views a workout in DukeCooks.
 */
public class ViewWorkoutCommand extends ViewCommand {

    public static final String VARIANT_WORD = "workout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + VARIANT_WORD + ": Views a workout in the shown list with"
            + "the specified index Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "You are viewing Workout : %1$s";

    private final Index targetIndex;

    /**
     * Creates an ViewWorkoutCommand to view the specified Diary
     */
    public ViewWorkoutCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Workout> lastShownList = model.getFilteredWorkoutList();

        int index = targetIndex.getZeroBased();

        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
        }

        Workout workoutToShow = lastShownList.get(index);

        return new CommandResult(String.format(MESSAGE_SUCCESS, workoutToShow),
                false, false, false, false, workoutToShow, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewWorkoutCommand)
                && targetIndex.equals(((ViewWorkoutCommand) other).targetIndex);
    }
}

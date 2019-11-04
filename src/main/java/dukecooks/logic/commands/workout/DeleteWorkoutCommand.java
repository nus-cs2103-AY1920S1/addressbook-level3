package dukecooks.logic.commands.workout;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.Workout;

/**
 * Deletes a workout identified using it's displayed index from Duke Cooks.
 */
public class DeleteWorkoutCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "workout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + VARIANT_WORD
            + ": Deletes the workout identified by the index number used in the displayed workout list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 1";

    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Deleted Workout: %1$s";

    private final Index targetIndex;

    public DeleteWorkoutCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Workout> lastShownList = model.getFilteredWorkoutList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
        }

        Workout workoutToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteWorkout(workoutToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SUCCESS, workoutToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteWorkoutCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteWorkoutCommand) other).targetIndex)); // state check
    }
}

package dukecooks.logic.commands.workout;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.RunCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.Workout;


/**
 * Runs a Workout in DukeCooks.
 */
public class RunWorkoutCommand extends RunCommand {

    public static final String VARIANT_WORD = "workout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + VARIANT_WORD
            + ": Runs the workout identified by the index number used in the displayed workout list.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + "1";

    public static final String MESSAGE_RUN_WORKOUT_RUN_SUCCESS = "Workout in progress! Work hard!";

    public static final String MESSAGE_WORKOUT_HAS_NO_EXERCISE = "Sorry, the workout you're running"
            + " has no exercises!\nPerhaps you might want to add some?";

    private final Index targetIndex;

    public RunWorkoutCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Workout> lastShownList = model.getFilteredWorkoutList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
        }
        Workout workoutToRun = lastShownList.get(targetIndex.getZeroBased());
        if (workoutToRun.getExercises().isEmpty()) {
            throw new CommandException(MESSAGE_WORKOUT_HAS_NO_EXERCISE);
        }
        return new CommandResult(MESSAGE_RUN_WORKOUT_RUN_SUCCESS, false, false, false, true,
                workoutToRun, false);
    }
}

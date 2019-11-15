package dukecooks.logic.commands.exercise;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SETS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TIMING;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.PushCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.details.ExerciseDetail;

/**
 * Pushes an Exercise into a Workout.
 */
public class PushExerciseCommand extends PushCommand {

    public static final String VARIANT_WORD = "exercise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pushes an item to a component of Duke Cooks. "
            + "Parameters: "
            + "INDEX (must be a positive integer)"
            + "[" + PREFIX_DISTANCE + "DISTANCE]..."
            + "[" + PREFIX_REPETITIONS + "REPS]..."
            + "[" + PREFIX_SETS + "SET]..."
            + "[" + PREFIX_WEIGHT + "WEIGHT]..."
            + "[" + PREFIX_TIMING + "TIME]...\n"
            + COMMAND_WORD + " " + ""
            + "Example: " + COMMAND_WORD + " "
            + VARIANT_WORD
            + "1 " + PREFIX_EXERCISE_INDEX + "1 "
            + PREFIX_SETS + "5 "
            + PREFIX_REPETITIONS + "5";

    public static final String MESSAGE_SUCCESS = "Exercise successfully added to the workout!\n"
            + "Add another?";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists in the workout!";

    private final Index workoutIndex;
    private final Index exerciseIndex;
    private final Set<ExerciseDetail> exerciseDetails;

    /**
     * Creates a PushExerciseCommand to push the specified {@code Exercise}
     */
    public PushExerciseCommand(Index workoutIndex, Index exerciseIndex,
                               Set<ExerciseDetail> exerciseDetails) {
        requireAllNonNull(workoutIndex, exerciseIndex, exerciseDetails);
        this.workoutIndex = workoutIndex;
        this.exerciseIndex = exerciseIndex;
        this.exerciseDetails = exerciseDetails;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Workout workout = getWorkout(model, workoutIndex);
        Exercise exerciseToPush = getExerciseToPush(model, exerciseIndex);
        Workout newWorkout;
        if (exerciseDetails.isEmpty()) {
            newWorkout = workout.pushExercise(exerciseToPush);
        } else {
            newWorkout = workout.pushExercise(exerciseToPush, exerciseDetails);
        }
        model.setWorkout(workout, newWorkout);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private Exercise getExerciseToPush(Model model, Index targetIndex) throws CommandException {
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    private Workout getWorkout(Model model, Index targetIndex) throws CommandException {
        List<Workout> lastShownList = model.getFilteredWorkoutList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PushExerciseCommand)
                && workoutIndex.equals(((PushExerciseCommand) other).workoutIndex)
                && exerciseIndex.equals(((PushExerciseCommand) other).exerciseIndex)
                && exerciseDetails.equals(((PushExerciseCommand) other).exerciseDetails);
    }
}

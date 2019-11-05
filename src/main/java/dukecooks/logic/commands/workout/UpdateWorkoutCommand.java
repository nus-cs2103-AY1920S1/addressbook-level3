package dukecooks.logic.commands.workout;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import dukecooks.logic.Logic;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.history.ExerciseRun;
import dukecooks.model.workout.history.WorkoutRun;

/**
 * Updates workout history with the new workout run.
 */
public class UpdateWorkoutCommand {

    public static final String MESSAGE_SUCCESS = "Workout completed! Good Job!";

    private final WorkoutRun workoutRun;
    private final Workout workoutRan;

    /**
     * Creates an UpdateWorkoutCommand to add the specified {@code WorkoutRun}
     */
    public UpdateWorkoutCommand(WorkoutRun workoutRun, Workout workoutRan) {
        requireNonNull(workoutRun);
        this.workoutRun = workoutRun;
        this.workoutRan = workoutRan;
    }

    /**
     * Modifies model as per the specification of update workout.
     */

    public CommandResult execute(Logic logic) {
        requireNonNull(logic);
        Model model = logic.getModel();
        model.setWorkout(workoutRan, workoutRan.updateHistory(workoutRun));
        model.updateFilteredWorkoutList(Model.PREDICATE_SHOW_ALL_WORKOUT);
        ArrayList<ExerciseName> exerciseNames = workoutRan.getExercises();
        ArrayList<ExerciseRun> exerciseRuns = workoutRun.getExercisesRan();
        for (int i = 0; i < exerciseNames.size(); i++) {
            ExerciseName exerciseName = exerciseNames.get(i);
            ExerciseRun exerciseRun = exerciseRuns.get(i);
            Exercise correspondingExercise = model.findExercise(exerciseName);
            model.setExercise(correspondingExercise, correspondingExercise
                    .updateHistory(exerciseRun));
        }
        logic.updateWorkoutStorage();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateWorkoutCommand // instanceof handles nulls
                && workoutRun.equals(((UpdateWorkoutCommand) other).workoutRun));
    }


}

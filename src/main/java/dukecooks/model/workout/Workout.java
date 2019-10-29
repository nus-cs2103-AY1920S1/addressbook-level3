package dukecooks.model.workout;

import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.details.ExerciseDetail;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a Workout in the WorkoutPlanner.
 */
public class Workout {

    private ArrayList<Exercise> exercises;
    private ArrayList<Set<ExerciseDetail>> exercisesDetails;

}

package dukecooks.model.workout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.history.WorkoutHistory;

/**
 * Represents a Workout in the WorkoutPlanner.
 */
public class Workout {

    private final WorkoutName name;
    private final ArrayList<ExerciseName> exercises;
    private final ArrayList<Set<ExerciseDetail>> exercisesDetails;
    private final Set<MuscleType> musclesTrained;
    private final Intensity averageIntensity;
    private final WorkoutHistory history;

    public Workout(WorkoutName name) {
        this.name = name;
        exercises = new ArrayList<>();
        exercisesDetails = new ArrayList<>();
        musclesTrained = new HashSet<>();
        averageIntensity = Intensity.LOW;
        history = new WorkoutHistory(new ArrayList<>());
    }

    public Workout(WorkoutName name, ArrayList<ExerciseName> exercises, ArrayList<Set<ExerciseDetail>> exercisesDetails,
                   Set<MuscleType> musclesTrained, Intensity averageIntensity, WorkoutHistory history) {
        this.name = name;
        this.exercises = exercises;
        this.exercisesDetails = exercisesDetails;
        this.musclesTrained = musclesTrained;
        this.averageIntensity = averageIntensity;
        this.history = history;
    }

    /**
     * Returns a new workout with the exercise added
     *
     */

    public Workout pushExercise(Exercise exercise, Set<ExerciseDetail> newExerciseDetails) {
        ArrayList<ExerciseName> newExercises = new ArrayList<>(exercises);
        newExercises.add(exercise.getExerciseName());
        ArrayList<Set<ExerciseDetail>> newExercisesDetails = new ArrayList<>(exercisesDetails);
        newExercisesDetails.add(newExerciseDetails);
        Set<MuscleType> newMusclesTrained = new HashSet<>(musclesTrained);
        newMusclesTrained.add(exercise.getMusclesTrained().getPrimaryMuscle());
        newMusclesTrained.addAll(exercise.getMusclesTrained().getSecondaryMuscles());
        Intensity newAverageIntensity = getNewAverageIntensity(averageIntensity,
                exercise.getIntensity(), newExercises.size());
        return new Workout(name, newExercises, newExercisesDetails, newMusclesTrained, newAverageIntensity, history);
    }

    /**
     * Returns a new workout with the exercise added , using the default exercise details.
     *
     */

    public Workout pushExercise(Exercise exercise) {
        return pushExercise(exercise, exercise.getExerciseDetails());
    }

    private Intensity getNewAverageIntensity(Intensity oldAverageIntensity, Intensity intensity, int size) {
        double totalIntensity = oldAverageIntensity.toInt() * (size - 1);
        totalIntensity += intensity.toInt();
        Integer newAverageIntensity = (int) Math.round((totalIntensity / (size)));
        try {
            return WorkoutPlannerParserUtil.parseIntensity(newAverageIntensity.toString());
        } catch (ParseException ex) {
            throw new AssertionError("Shouldn't get here");
        }
    }

    public ArrayList<ExerciseName> getExercises() {
        return exercises;
    }

    public Set<MuscleType> getMusclesTrained() {
        return musclesTrained;
    }

    public ArrayList<Set<ExerciseDetail>> getExercisesDetails() {
        return exercisesDetails;
    }

    public Intensity getAverageIntensity() {
        return averageIntensity;
    }

    public WorkoutName getName() {
        return name;
    }

    public WorkoutHistory getHistory() {
        return history;
    }


    /**
     * Returns true if both exercises of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two exercises.
     */
    public boolean isSameWorkout(Workout otherWorkout) {
        if (otherWorkout == this) {
            return true;
        }

        return otherWorkout != null
                && otherWorkout.getName().equals(getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Workout)) {
            return false;
        }

        Workout otherWorkout = (Workout) other;
        return otherWorkout.getName().equals(getName())
                && otherWorkout.getMusclesTrained().equals(getMusclesTrained())
                && otherWorkout.getAverageIntensity().equals(getAverageIntensity())
                && otherWorkout.getExercisesDetails().equals(getExercisesDetails())
                && otherWorkout.getExercises().equals(getExercises())
                && otherWorkout.getHistory().equals(getHistory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, exercises, exercisesDetails, musclesTrained, averageIntensity, history);
    }
}

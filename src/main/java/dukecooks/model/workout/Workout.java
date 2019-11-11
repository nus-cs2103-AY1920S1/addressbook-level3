package dukecooks.model.workout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;
import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Sets;
import dukecooks.model.workout.exercise.details.Timing;
import dukecooks.model.workout.history.WorkoutHistory;
import dukecooks.model.workout.history.WorkoutRun;

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
     * Returns a new workout with WorkoutHistory updated with the workout run.
     */

    public Workout updateHistory(WorkoutRun workoutRun) {
        WorkoutHistory updatedHistory = history.addRun(workoutRun);
        return new Workout(name, exercises, exercisesDetails, musclesTrained, averageIntensity, updatedHistory);
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
     * Creates exercise set attempts based of the exercises details.
     */
    public ArrayList<ExerciseSetAttempt> getExerciseSetAttempts() {
        ArrayList<ExerciseSetAttempt> toReturn = new ArrayList<>();
        toReturn.addAll(exercisesDetails.stream()
                .map(this::getExerciseSetAttempt).collect(Collectors.toList()));
        return toReturn;
    }

    /**
     * Creates exercise set attempts from a set of Exercise Details.
     */
    private ExerciseSetAttempt getExerciseSetAttempt(Set<ExerciseDetail> details) {
        Repetitions reps = null;
        Timing timing = null;
        Distance distance = null;
        ExerciseWeight weight = null;
        for (ExerciseDetail detail : details) {
            if (detail instanceof Repetitions) {
                reps = (Repetitions) detail;
            } else if (detail instanceof Timing) {
                timing = (Timing) detail;
            } else if (detail instanceof ExerciseWeight) {
                weight = (ExerciseWeight) detail;
            } else if (detail instanceof Distance) {
                distance = (Distance) detail;
            }
        }
        return new ExerciseSetAttempt(weight, distance, reps, timing, null);
    }

    /**
     * Returns the Sets detail from the Set of exercise detail of index specified.
     */
    public Sets getSet(int index) {
        Set<ExerciseDetail> detailSet = exercisesDetails.get(index);
        for (ExerciseDetail detail : detailSet) {
            if (detail instanceof Sets) {
                return (Sets) detail;
            }
        }
        return new Sets(1);
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

    @Override
    public String toString() {
        return name.toString();
    }
}

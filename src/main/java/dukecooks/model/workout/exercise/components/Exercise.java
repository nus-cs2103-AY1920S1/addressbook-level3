package dukecooks.model.workout.exercise.components;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.history.ExerciseHistory;

/**
 * Represents an Exercise in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise {

    // Identity fields
    private final ExerciseName exerciseName;
    private final MusclesTrained musclesTrained;
    private final Intensity intensity;

    // Data fields
    private final Set<ExerciseDetail> exerciseDetails = new HashSet<>();
    private final ExerciseHistory history;

    /**
     * Every field except ExerciseHistory must be present and not null.
     */
    public Exercise(ExerciseName exerciseName, MusclesTrained musclesTrained,
                    Intensity intensity, Set<ExerciseDetail> exerciseDetails) {
        requireAllNonNull(exerciseName, musclesTrained, intensity, exerciseDetails);
        this.exerciseName = exerciseName;
        this.musclesTrained = musclesTrained;
        this.intensity = intensity;
        this.exerciseDetails.addAll(exerciseDetails);
        history = new ExerciseHistory(new ArrayList<>());
    }

    /**
     * Every field except ExerciseHistory must be present and must not null.
     */
    public Exercise(ExerciseName exerciseName, MusclesTrained musclesTrained,
                    Intensity intensity, Set<ExerciseDetail> exerciseDetails, ExerciseHistory history) {
        requireAllNonNull(exerciseName, musclesTrained, intensity, exerciseDetails, history);
        this.exerciseName = exerciseName;
        this.musclesTrained = musclesTrained;
        this.intensity = intensity;
        this.exerciseDetails.addAll(exerciseDetails);
        this.history = history;
    }

    public ExerciseName getExerciseName() {
        return exerciseName;
    }

    public MusclesTrained getMusclesTrained() {
        return musclesTrained;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public ExerciseHistory getHistory() {
        return history;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<ExerciseDetail> getExerciseDetails() {
        return Collections.unmodifiableSet(exerciseDetails);
    }

    /**
     * Returns true if both exercises of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two exercises.
     */
    public boolean isSameExercise(Exercise otherExercise) {
        if (otherExercise == this) {
            return true;
        }

        return otherExercise != null
                && otherExercise.getExerciseName().equals(getExerciseName());
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     * This defines a stronger notion of equality between two exercises.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Exercise)) {
            return false;
        }

        Exercise otherExercise = (Exercise) other;
        return otherExercise.getExerciseName().equals(getExerciseName())
                && otherExercise.getMusclesTrained().equals(getMusclesTrained())
                && otherExercise.getIntensity().equals(getIntensity())
                && otherExercise.getExerciseDetails().equals(getExerciseDetails())
                && otherExercise.getHistory().equals(getHistory());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(exerciseName, musclesTrained, intensity, exerciseDetails, history);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getExerciseName())
                .append(" Muscle Type: ")
                .append(getMusclesTrained())
                .append(" Intensity: ")
                .append(getIntensity())
                .append(" Details: ");
        getExerciseDetails().forEach(builder::append);
        return builder.toString();
    }
}

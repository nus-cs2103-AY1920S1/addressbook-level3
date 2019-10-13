package seedu.address.model.exercise;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.details.ExerciseDetail;

/**
 * Represents an Exercise in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise {

    // Identity fields
    private final Name name;
    private final MusclesTrained musclesTrained;
    private final Intensity intensity;

    // Data fields
    private final Set<ExerciseDetail> exerciseDetails = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Exercise(Name name, MusclesTrained musclesTrained, Intensity intensity, Set<ExerciseDetail> exerciseDetails) {
        requireAllNonNull(name, musclesTrained, intensity, exerciseDetails);
        this.name = name;
        this.musclesTrained = musclesTrained;
        this.intensity = intensity;
        this.exerciseDetails.addAll(exerciseDetails);
    }

    public Name getName() {
        return name;
    }

    public MusclesTrained getMusclesTrained() {
        return musclesTrained;
    }

    public Intensity getIntensity() {
        return intensity;
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
                && otherExercise.getName().equals(getName());
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
        return otherExercise.getName().equals(getName())
                && otherExercise.getMusclesTrained().equals(getMusclesTrained())
                && otherExercise.getIntensity().equals(getIntensity())
                && otherExercise.getExerciseDetails().equals(getExerciseDetails());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, musclesTrained, intensity, exerciseDetails);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Muscle Type: ")
                .append(getMusclesTrained())
                .append(" Intensity: ")
                .append(getIntensity())
                .append(" Tags: ");
        getExerciseDetails().forEach(builder::append);
        return builder.toString();
    }

}

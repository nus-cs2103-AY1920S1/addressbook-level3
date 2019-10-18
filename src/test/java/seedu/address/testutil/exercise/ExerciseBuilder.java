package seedu.address.testutil.exercise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.exercise.components.ExerciseName;
import seedu.address.model.exercise.components.Intensity;
import seedu.address.model.exercise.components.MuscleType;
import seedu.address.model.exercise.components.MusclesTrained;
import seedu.address.model.exercise.details.ExerciseDetail;
import seedu.address.model.exercise.details.unit.DistanceUnit;
import seedu.address.model.exercise.details.unit.WeightUnit;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class ExerciseBuilder {

    public static final String DEFAULT_NAME = "Bench Press";
    public static final Intensity DEFAULT_INTENSITY = Intensity.MEDIUM;
    public static final MuscleType DEFAULT_PRIMARY_MUSCLE = new MuscleType("Chest");
    public static final MusclesTrained DEFAULT_MUSCLES_TRAINED = new MusclesTrained(DEFAULT_PRIMARY_MUSCLE,
            new ArrayList<MuscleType>());

    private ExerciseName exerciseName;
    private Intensity intensity;
    private MusclesTrained musclesTrained;
    private Set<ExerciseDetail> exerciseDetails;

    public ExerciseBuilder() {
        exerciseName = new ExerciseName(DEFAULT_NAME);
        musclesTrained = DEFAULT_MUSCLES_TRAINED;
        intensity = DEFAULT_INTENSITY;
        exerciseDetails = new HashSet<>();
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code personToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        exerciseName = exerciseToCopy.getExerciseName();
        intensity = exerciseToCopy.getIntensity();
        musclesTrained = exerciseToCopy.getMusclesTrained();
        exerciseDetails = new HashSet<>(exerciseToCopy.getExerciseDetails());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExerciseBuilder withName(String name) {
        this.exerciseName = new ExerciseName(name);
        return this;
    }

    /**
     * Sets the {@code MuscleTrained} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withMusclesTrained(MusclesTrained musclesTrained) {
        this.musclesTrained = musclesTrained;
        return this;
    }

    /**
     * Sets the {@code MuscleTrained} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withMusclesTrained(String... muscleTypes) {
        MuscleType primaryMuscle = new MuscleType(muscleTypes[0]);
        ArrayList<MuscleType> secondaryMuscles = new ArrayList<>();
        for (int i = 1; i < muscleTypes.length; i++) {
            MuscleType secondaryMuscle = new MuscleType(muscleTypes[i]);
            secondaryMuscles.add(secondaryMuscle);
        }
        this.musclesTrained = new MusclesTrained(primaryMuscle, secondaryMuscles);
        return this;
    }

    /**
     * Sets the {@code Intensity} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withIntensity(Intensity intensity) {
        this.intensity = intensity;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public ExerciseBuilder withDetails(Float weight, WeightUnit weightUnit, Float distance,
                                       DistanceUnit distanceUnit, Integer reps, Integer sets) {
        this.exerciseDetails = SampleDataUtil.getDetails(weight, weightUnit, distance,
                distanceUnit, reps, sets);
        return this;
    }


    public Exercise build() {
        return new Exercise(exerciseName, musclesTrained, intensity, exerciseDetails);
    }

}

package dukecooks.testutil.exercise;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import dukecooks.model.util.SampleDataUtil;
import dukecooks.model.workout.WorkoutName;
import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.components.MusclesTrained;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Sets;
import dukecooks.model.workout.exercise.details.unit.DistanceUnit;
import dukecooks.model.workout.exercise.details.unit.WeightUnit;
import dukecooks.model.workout.history.ExerciseHistory;
import dukecooks.model.workout.history.ExerciseRun;

/**
 * A utility class to help with building Person objects.
 */
public class ExerciseBuilder {

    public static final String DEFAULT_NAME = "Bench Press";
    public static final Intensity DEFAULT_INTENSITY = Intensity.MEDIUM;
    public static final MuscleType DEFAULT_PRIMARY_MUSCLE = new MuscleType("Chest");
    public static final MusclesTrained DEFAULT_MUSCLES_TRAINED = new MusclesTrained(DEFAULT_PRIMARY_MUSCLE,
            new ArrayList<MuscleType>());
    public static final ExerciseSetAttempt DEFAULT_SET_ATTEMPT = new ExerciseSetAttempt(new ExerciseWeight(Float
            .valueOf(10), WeightUnit.KILOGRAM), null, new Repetitions(5), null, null);
    public static final ExerciseRun DEFAULT_EXERCISE_RUN_1 = new ExerciseRun(LocalDateTime
            .of(2019, 11, 10, 18, 20), LocalDateTime
            .of(2019, 11, 10, 19, 20),
            new Sets(5), new Sets(5), new ArrayList(Arrays.asList(DEFAULT_SET_ATTEMPT)),
            new WorkoutName("Tabata"));
    public static final ExerciseRun DEFAULT_EXERCISE_RUN_2 = new ExerciseRun(LocalDateTime
            .of(2019, 11, 10, 18, 19), LocalDateTime
            .of(2019, 11, 10, 19, 20),
            new Sets(5), new Sets(5), new ArrayList(Arrays.asList(DEFAULT_SET_ATTEMPT)),
            new WorkoutName("Tabata"));
    public static final ExerciseHistory DEFAULT_EXERCISE_HISTORY = new ExerciseHistory(new ArrayList(Arrays
            .asList(DEFAULT_EXERCISE_RUN_1)));

    private ExerciseName exerciseName;
    private Intensity intensity;
    private MusclesTrained musclesTrained;
    private Set<ExerciseDetail> exerciseDetails;
    private ExerciseHistory history;

    public ExerciseBuilder() {
        exerciseName = new ExerciseName(DEFAULT_NAME);
        musclesTrained = DEFAULT_MUSCLES_TRAINED;
        intensity = DEFAULT_INTENSITY;
        exerciseDetails = new HashSet<>();
        history = DEFAULT_EXERCISE_HISTORY;
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code personToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        exerciseName = exerciseToCopy.getExerciseName();
        intensity = exerciseToCopy.getIntensity();
        musclesTrained = exerciseToCopy.getMusclesTrained();
        exerciseDetails = new HashSet<>(exerciseToCopy.getExerciseDetails());
        history = exerciseToCopy.getHistory();
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
        musclesTrained = new MusclesTrained(primaryMuscle, secondaryMuscles);
        return withMusclesTrained(musclesTrained);
    }

    /**
     * Sets the {@code Intensity} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withIntensity(Intensity intensity) {
        this.intensity = intensity;
        return this;
    }

    /**
     * Sets the {@code ExerciseHistory} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withHistory(ExerciseHistory history) {
        this.history = history;
        return this;
    }

    /**
     * Sets the {@code ExerciseHistory} of the {@code Exercise} that we are building as empty.
     */
    public ExerciseBuilder withEmptyHistory() {
        this.history = new ExerciseHistory(new ArrayList<>());
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
        return new Exercise(exerciseName, musclesTrained, intensity, exerciseDetails, history);
    }

}

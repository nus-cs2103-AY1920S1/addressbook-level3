package dukecooks.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.WorkoutCatalogue;
import dukecooks.model.workout.WorkoutName;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.components.MusclesTrained;
import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Sets;
import dukecooks.model.workout.exercise.details.unit.DistanceUnit;
import dukecooks.model.workout.exercise.details.unit.WeightUnit;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {

    public static Exercise[] getSampleExercises() {
        return new Exercise[]{
            new Exercise(new ExerciseName("Pushups"),
                        getMuscleTrained("Chest", "Biceps", "Back", "Shoulders"),
                        Intensity.HIGH,
                        getDetails(null, null, null, null, 30, 3)),
            new Exercise(new ExerciseName("Sprint Sets"),
                        getMuscleTrained("Cardiovascular", "Thighs", "Calves"),
                        Intensity.HIGH,
                        getDetails(null, null, (float) 400, DistanceUnit.METER, null, 8)),
            new Exercise(new ExerciseName("Planks"),
                    getMuscleTrained("Abs", "Shoulders"), Intensity.MEDIUM,
                    getDetails(null, null, null, null, 30, 3)),
            new Exercise(new ExerciseName("Bicep Curl"),
                    getMuscleTrained("Biceps"), Intensity.LOW,
                    getDetails((float) 10.0, WeightUnit.KILOGRAM, null, null, 12, 4)),
            new Exercise(new ExerciseName("Jumping Jacks"),
                    getMuscleTrained("Cardiovascular"), Intensity.MEDIUM,
                    getDetails(null, null, null, null, 100, null)),
            new Exercise(new ExerciseName("Weighted Sprints"),
                    getMuscleTrained("Cardiovascular", "Thighs", "Hamstrings", "Calves"),
                    Intensity.HIGH,
                    getDetails((float) 500, WeightUnit.GRAM, (float) 400, DistanceUnit.METER, null, 8))
        };
    }

    /**
     * Returns a MuscleTrained containing the primary muscles and list of secondary muscles given.
     */
    public static MusclesTrained getMuscleTrained(String primary, String... secondary) {
        MuscleType primaryMuscle = new MuscleType(primary);
        ArrayList<MuscleType> secondaryMuscles = new ArrayList<>();
        secondaryMuscles.addAll(Arrays.stream(secondary).map(MuscleType::new).collect(Collectors.toList()));
        return new MusclesTrained(primaryMuscle, secondaryMuscles);
    }

    public static Set<ExerciseDetail> getDetails(Float weight, WeightUnit weightUnit, Float distance,
                                                 DistanceUnit distanceUnit, Integer reps, Integer sets) {
        Set<ExerciseDetail> details = new HashSet<>();
        ExerciseWeight weightDetail = weight == null ? null : new ExerciseWeight(weight, weightUnit);
        Distance distanceDetail = distance == null ? null : new Distance(distance, distanceUnit);
        Repetitions repsDetail = reps == null ? null : new Repetitions(reps);
        Sets setsDetail = sets == null ? null : new Sets(sets);

        addIfNotNull(details, weightDetail);
        addIfNotNull(details, distanceDetail);
        addIfNotNull(details, repsDetail);
        addIfNotNull(details, setsDetail);
        return details;
    }

    /**
     * Checks if {@code detail} is null and adds to {@code details}
     * if it isn't.
     *
     * @param details A set containing all the ExerciseDetails of an exercise
     * @param detail  A detail that is possibly null value
     */

    public static void addIfNotNull(Set<ExerciseDetail> details, ExerciseDetail detail) {
        if (detail != null) {
            details.add(detail);
        }
    }

    public static Workout[] getSampleWorkout() {
        return new Workout[]{
            new Workout(new WorkoutName("Hardcore Parkour")),
            new Workout(new WorkoutName("Tabata"))
                    .pushExercise(new Exercise(new ExerciseName("Pushups"),
                    getMuscleTrained("Chest", "Biceps", "Back", "Shoulders"),
                    Intensity.HIGH,
                    getDetails(null, null, null, null, 30, 3)))
        };
    }

    public static ReadOnlyExerciseCatalogue getSampleExerciseCatalogue() {
        ExerciseCatalogue exerciseCatalogue = new ExerciseCatalogue();
        for (Exercise sampleExercise : getSampleExercises()) {
            exerciseCatalogue.addExercise(sampleExercise);
        }
        return exerciseCatalogue;
    }

    public static ReadOnlyWorkoutCatalogue getSampleWorkoutCatalogue() {
        WorkoutCatalogue workoutCatalogue = new WorkoutCatalogue();
        for (Workout sampleWorkout : getSampleWorkout()) {
            workoutCatalogue.addWorkout(sampleWorkout);
        }
        return workoutCatalogue;
    }


}

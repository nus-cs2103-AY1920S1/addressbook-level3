package dukecooks.storage.workout;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.WorkoutName;
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
import dukecooks.storage.workout.exercise.JsonAdaptedDistance;
import dukecooks.storage.workout.exercise.JsonAdaptedExerciseDetail;
import dukecooks.storage.workout.exercise.JsonAdaptedMuscleType;
import dukecooks.storage.workout.exercise.JsonAdaptedRepetitions;
import dukecooks.storage.workout.exercise.JsonAdaptedSets;
import dukecooks.storage.workout.exercise.JsonAdaptedTiming;
import dukecooks.storage.workout.exercise.JsonAdaptedWeight;

/**
 * Jackson friendly version of Workout.
 */
public class JsonAdaptedWorkout {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Workout's %s field is missing!";

    private final String name;
    private final List<String> exercises = new ArrayList<>();
    private final Set<JsonAdaptedMuscleType> musclesTrained = new HashSet<>();
    private final List<List<JsonAdaptedExerciseDetail>> exercisesDetails = new ArrayList<>();
    private final String averageIntensity;
    private final List<JsonAdaptedWorkoutRun> previousRuns = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWorkout} with the given workout details.
     */
    @JsonCreator
    public JsonAdaptedWorkout(@JsonProperty("name") String name,
                               @JsonProperty("exercises") List<String> exercises,
                               @JsonProperty("musclesTrained") Set<JsonAdaptedMuscleType> musclesTrained,
                               @JsonProperty("exercisesDetails") List<List<JsonAdaptedExerciseDetail>> exercisesDetails,
                               @JsonProperty("averageIntensity") String averageIntensity,
                               @JsonProperty("noTimesRan") Integer noTimesRan,
                               @JsonProperty("averageRunTime") Duration averageRunTime,
                               @JsonProperty("previousRuns") List<JsonAdaptedWorkoutRun> previousRuns) {
        this.name = name;
        if (exercises != null) {
            this.exercises.addAll(exercises);
        }
        if (musclesTrained != null) {
            this.musclesTrained.addAll(musclesTrained);
        }
        if (exercisesDetails != null) {
            this.exercisesDetails.addAll(exercisesDetails);
        }
        this.averageIntensity = averageIntensity;
        if (previousRuns != null) {
            this.previousRuns.addAll(previousRuns);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */

    public JsonAdaptedWorkout(Workout source) {
        name = source.getName().workoutName;
        exercises.addAll(source.getExercises().stream()
                .map(name -> name.toString())
                .collect(Collectors.toList()));
        musclesTrained.addAll(source.getMusclesTrained().stream()
                .map(JsonAdaptedMuscleType::new)
                .collect(Collectors.toList()));
        exercisesDetails.addAll(source.getExercisesDetails().stream()
                .map(set -> set.stream()
                        .map(detail -> toAdaptedJson(detail))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList()));
        averageIntensity = source.getAverageIntensity().toString();
        previousRuns.addAll(source.getHistory().getPreviousRuns().stream()
                .map(JsonAdaptedWorkoutRun::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts an Exercise Detail into its corresponding Json-Adapted class
     * @param detail The exercise detail to be converted
     * @return returns an JsonAdaptedExerciseDetail that is of the correct class
     */

    private JsonAdaptedExerciseDetail toAdaptedJson (ExerciseDetail detail) {
        if (detail instanceof ExerciseWeight) {
            return new JsonAdaptedWeight((ExerciseWeight) detail);
        } else if (detail instanceof Distance) {
            return new JsonAdaptedDistance((Distance) detail);
        } else if (detail instanceof Repetitions) {
            return new JsonAdaptedRepetitions((Repetitions) detail);
        } else if (detail instanceof Sets) {
            return new JsonAdaptedSets((Sets) detail);
        } else {
            return new JsonAdaptedTiming((Timing) detail);
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Workout} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted workout.
     */
    public Workout toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String
                    .format(MISSING_FIELD_MESSAGE_FORMAT, WorkoutName.class.getSimpleName()));
        }
        if (!WorkoutName.isValidName(name)) {
            throw new IllegalValueException(WorkoutName.MESSAGE_CONSTRAINTS);
        }
        final WorkoutName modelWorkoutName = new WorkoutName(name);

        final ArrayList<ExerciseName> modelExercises = new ArrayList<>();
        for (String exercise : exercises) {
            modelExercises.add(new ExerciseName(exercise));
        }

        final Set<MuscleType> modelMusclesTrained = new HashSet<>();
        for (JsonAdaptedMuscleType muscleType : musclesTrained) {
            modelMusclesTrained.add(muscleType.toModelType());
        }

        final ArrayList<Set<ExerciseDetail>> modelExercisesDetails = new ArrayList<>();
        for (List<JsonAdaptedExerciseDetail> exerciseDetails : exercisesDetails) {
            final List<ExerciseDetail> modelExerciseDetails = new ArrayList<>();
            for (JsonAdaptedExerciseDetail detail : exerciseDetails) {
                modelExerciseDetails.add(detail.toModelType());
            }
            modelExercisesDetails.add(new HashSet<ExerciseDetail>(modelExerciseDetails));
        }

        if (averageIntensity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Intensity.class.getSimpleName()));
        }
        if (!Intensity.isValidIntensity(averageIntensity)) {
            throw new IllegalValueException(Intensity.MESSAGE_CONSTRAINTS);
        }
        final Intensity modelAverageIntensity = WorkoutPlannerParserUtil
                .parseIntensity(averageIntensity);

        final ArrayList<WorkoutRun> modelWorkoutRun = new ArrayList<>();
        for (JsonAdaptedWorkoutRun run : previousRuns) {
            modelWorkoutRun.add(run.toModelType());
        }

        final WorkoutHistory modelHistory = new WorkoutHistory(modelWorkoutRun);

        return new Workout(modelWorkoutName, modelExercises, modelExercisesDetails,
                modelMusclesTrained, modelAverageIntensity, modelHistory);
    }
}

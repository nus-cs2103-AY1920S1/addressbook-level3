package dukecooks.storage.workout.exercise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;
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
import dukecooks.model.workout.exercise.details.Timing;
import dukecooks.model.workout.history.ExerciseHistory;
import dukecooks.model.workout.history.ExerciseRun;

/**
 * Jackson-friendly version of {@link Exercise}.
 */
public class JsonAdaptedExercise {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";

    private final String name;
    private final String primaryMuscle;
    private final List<JsonAdaptedMuscleType> secondaryMuscles = new ArrayList<>();
    private final String intensity;
    private final List<JsonAdaptedExerciseDetail> details = new ArrayList<>();
    private final List<JsonAdaptedExerciseRun> previousRuns = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedExercise} with the given exercise details.
     */
    @JsonCreator
    public JsonAdaptedExercise(@JsonProperty("name") String name,
                               @JsonProperty("primaryMuscle") String primaryMuscle,
                               @JsonProperty("secondaryMuscles") List<JsonAdaptedMuscleType> secondaryMuscles,
                               @JsonProperty("intensity") String intensity,
                               @JsonProperty("details") List<JsonAdaptedExerciseDetail> details,
                               @JsonProperty("previousRuns") List<JsonAdaptedExerciseRun> previousRuns) {
        this.name = name;
        this.primaryMuscle = primaryMuscle;
        if (secondaryMuscles != null) {
            this.secondaryMuscles.addAll(secondaryMuscles);
        }
        this.intensity = intensity;
        if (details != null) {
            this.details.addAll(details);
        }
        this.previousRuns.addAll(previousRuns);
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedExercise(Exercise source) {
        name = source.getExerciseName().exerciseName;
        intensity = source.getIntensity().toString();
        primaryMuscle = source.getMusclesTrained().getPrimaryMuscle().toString();
        secondaryMuscles.addAll(source.getMusclesTrained().getSecondaryMuscles().stream()
                .map(JsonAdaptedMuscleType::new)
                .collect(Collectors.toList()));
        details.addAll(source.getExerciseDetails().stream()
                .map(exerciseDetail -> {
                    return toAdaptedJson(exerciseDetail);
                })
                .collect(Collectors.toList()));
        previousRuns.addAll(source.getHistory().getPreviousRuns()
                .stream().map(JsonAdaptedExerciseRun::new)
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
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Exercise toModelType() throws IllegalValueException {
        final ArrayList<ExerciseRun> runs = new ArrayList<>();
        for (JsonAdaptedExerciseRun jsonRun : previousRuns) {
            runs.add(jsonRun.toModelType());
        }

        final List<ExerciseDetail> exerciseDetails = new ArrayList<>();
        for (JsonAdaptedExerciseDetail detail : details) {
            exerciseDetails.add(detail.toModelType());
        }

        final ArrayList<MuscleType> secondaryMuscles = new ArrayList<>();
        for (JsonAdaptedMuscleType muscleType : this.secondaryMuscles) {
            secondaryMuscles.add(muscleType.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String
                    .format(MISSING_FIELD_MESSAGE_FORMAT, ExerciseName.class.getSimpleName()));
        }
        if (!ExerciseName.isValidName(name)) {
            throw new IllegalValueException(ExerciseName.MESSAGE_CONSTRAINTS);
        }
        final ExerciseName modelExerciseName = new ExerciseName(name);

        if (primaryMuscle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MuscleType.class.getSimpleName()));
        }
        if (!MuscleType.isValidMuscleType(primaryMuscle)) {
            throw new IllegalValueException(ExerciseName.MESSAGE_CONSTRAINTS);
        }
        final MuscleType modelPrimaryMuscle = new MuscleType(primaryMuscle);

        if (intensity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Intensity.class.getSimpleName()));
        }
        if (!Intensity.isValidIntensity(intensity)) {
            throw new IllegalValueException(ExerciseName.MESSAGE_CONSTRAINTS);
        }
        final Intensity modelIntensity = WorkoutPlannerParserUtil.parseIntensity(intensity);

        MusclesTrained modelMuscleTrained = new MusclesTrained(modelPrimaryMuscle, secondaryMuscles);
        final Set<ExerciseDetail> modelExerciseDetails = new HashSet<>(exerciseDetails);
        final ExerciseHistory history = new ExerciseHistory(runs);
        return new Exercise(modelExerciseName, modelMuscleTrained, modelIntensity, modelExerciseDetails, history);
    }

}

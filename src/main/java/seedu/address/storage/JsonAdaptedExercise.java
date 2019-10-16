package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.WorkoutPlannerParserUtil;
import seedu.address.model.details.Distance;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.ExerciseWeight;
import seedu.address.model.details.Repetitions;
import seedu.address.model.details.Sets;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseName;
import seedu.address.model.exercise.Intensity;
import seedu.address.model.exercise.MuscleType;
import seedu.address.model.exercise.MusclesTrained;

/**
 * Jackson-friendly version of {@link Exercise}.
 */
class JsonAdaptedExercise {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";

    private final String name;
    private final String primaryMuscle;
    private final List<JsonAdaptedMuscleType> secondaryMuscles = new ArrayList<>();
    private final String intensity;
    private final List<JsonAdaptedExerciseDetail> details = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedExercise(@JsonProperty("name") String name,
                               @JsonProperty("primaryMuscle") String primaryMuscle,
                               @JsonProperty("secondaryMuscles") List<JsonAdaptedMuscleType> secondaryMuscles,
                               @JsonProperty("intensity") String intensity,
                               @JsonProperty("details") List<JsonAdaptedExerciseDetail> details) {
        this.name = name;
        this.primaryMuscle = primaryMuscle;
        if (secondaryMuscles != null) {
            this.secondaryMuscles.addAll(secondaryMuscles);
        }
        this.intensity = intensity;
        if (details != null) {
            this.details.addAll(details);
        }
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
        } else {
            return new JsonAdaptedSets((Sets) detail);
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Exercise toModelType() throws IllegalValueException {
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
        return new Exercise(modelExerciseName, modelMuscleTrained, modelIntensity, modelExerciseDetails);
    }

}

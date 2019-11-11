package seedu.exercise.storage.resource;

import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.SortedUniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Jackson-friendly version of {@link Regime}.
 */
public class JsonAdaptedRegime extends JsonAdaptedResource<Regime> {

    private final String name;
    private final List<JsonAdaptedExercise> exercises = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRegime} with the given regime details.
     */
    public JsonAdaptedRegime(@JsonProperty("name") String name,
                             @JsonProperty("exercises") List<JsonAdaptedExercise> exercises) {
        this.name = name;
        if (exercises != null) {
            this.exercises.addAll(exercises);
        }
    }

    /**
     * Converts a given {@code Regime} into this class for Jackson use.
     */
    public JsonAdaptedRegime(Regime source) {
        name = source.getRegimeName().toString();
        exercises.addAll(source.getRegimeExercises().asUnmodifiableObservableList().stream()
            .map(JsonAdaptedExercise::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted regime object into the model's {@code Regime} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted regime.
     */
    public Regime toModelType() throws IllegalValueException {
        final SortedUniqueResourceList<Exercise> modelExercises =
                new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        for (JsonAdaptedExercise exercise : exercises) {
            modelExercises.add(exercise.toModelType());
        }

        final Name modelName = new Name(name);
        return new Regime(modelName, modelExercises);
    }
}

package seedu.exercise.storage.serializablebook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.storage.resource.JsonAdaptedExercise;

/**
 * An Immutable ExerciseBook that is serializable to JSON format.
 */
@JsonRootName(value = "exercisebook")
public class JsonSerializableExerciseBook extends SerializableResourceBook<JsonAdaptedExercise, Exercise> {

    /**
     * Constructs a {@code JsonSerializableExerciseBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableExerciseBook(@JsonProperty("jsonResources") List<JsonAdaptedExercise> exercises) {
        super(exercises);
    }

    /**
     * Converts a given {@code ReadOnlyResourceBook<Exercise>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExerciseBook}.
     */
    public JsonSerializableExerciseBook(ReadOnlyResourceBook<Exercise> source) {
        super(source, JsonAdaptedExercise.class);
    }

}

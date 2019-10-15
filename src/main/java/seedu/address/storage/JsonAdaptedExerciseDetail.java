package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.details.Distance;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.Sets;

/**
 * Jackson-friendly version of {@link ExerciseDetail}.
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typ")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedSets.class, name = "SET"),
        @JsonSubTypes.Type(value = JsonAdaptedDistance.class, name = "DST"),
        @JsonSubTypes.Type(value = JsonAdaptedRepetitions.class, name = "REP"),
        @JsonSubTypes.Type(value = JsonAdaptedWeight.class, name = "WGT")}
)
abstract class JsonAdaptedExerciseDetail<T> {

    protected T magnitude;
    public String type;

    public void Sets(){}
    public void Distance(){}
    public void Repetitions(){}
    public void Weight(){}

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise detail.
     */
    abstract public ExerciseDetail toModelType() throws IllegalValueException;

}

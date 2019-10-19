package seedu.exercise.storage.serializablebook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.storage.resource.JsonAdaptedRegime;

/**
 * An Immutable RegimeBook that is serializable to JSON format.
 */
@JsonRootName(value = "regimebook")
public class JsonSerializableRegimeBook extends SerializableResourceBook<JsonAdaptedRegime, Regime> {

    /**
     * Constructs a {@code JsonSerializableRegimeBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableRegimeBook(@JsonProperty("jsonResources") List<JsonAdaptedRegime> regimes) {
        super(regimes);
    }

    /**
     * Converts a given {@code ReadOnlyResourceBook<Regime>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRegimeBook}.
     */
    public JsonSerializableRegimeBook(ReadOnlyResourceBook<Regime> source) {
        super(source, JsonAdaptedRegime.class);
    }

}

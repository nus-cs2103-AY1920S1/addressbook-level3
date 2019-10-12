package seedu.exercise.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.ReadOnlyRegimeBook;
import seedu.exercise.model.RegimeBook;
import seedu.exercise.model.regime.Regime;

/**
 * An Immutable RegimeBook that is serializable to JSON format.
 */
@JsonRootName(value = "regimebook")
public class JsonSerializableRegimeBook {


    public static final String MESSAGE_DUPLICATE_REGIME = "Regime list contains duplicate regime(s).";

    private final List<JsonAdaptedRegime> regimes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRegimeBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableRegimeBook(@JsonProperty("regimes") List<JsonAdaptedRegime> regimes) {
        this.regimes.addAll(regimes);
    }

    /**
     * Converts a given {@code ReadOnlyRegimeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRegimeBook}.
     */
    public JsonSerializableRegimeBook(ReadOnlyRegimeBook source) {
        regimes.addAll(source.getRegimeList().stream().map(JsonAdaptedRegime::new).collect(Collectors.toList()));
    }

    /**
     * Converts this regime book into the model's {@code ExerciseBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RegimeBook toModelType() throws IllegalValueException {
        RegimeBook regimeBook = new RegimeBook();
        for (JsonAdaptedRegime jsonAdaptedRegime : regimes) {
            Regime regime = jsonAdaptedRegime.toModelType();
            if (regimeBook.hasRegime(regime)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REGIME);
            }
            regimeBook.addRegime(regime);
        }
        return regimeBook;
    }
}

package seedu.jarvis.storage.cca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;

/**
 * An Immutable {@code CcaTracker} that is serializable to JSON format.
 */
@JsonRootName(value = "ccatracker")
public class JsonSerializableCcaTracker {

    public static final String MESSAGE_DUPLICATE_CCA = "CcaList contains duplicate cca(s).";

    private final List<JsonAdaptedCca> ccas = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCcaTracker} with the given ccas.
     */
    @JsonCreator
    public JsonSerializableCcaTracker(@JsonProperty("ccas") List<JsonAdaptedCca> ccas) {
        this.ccas.addAll(ccas);
    }

    /**
     * Converts a given {@code CcaTracker} into this class for Jackson use.
     *
     * @param ccaTracker future changes to this will not affect the created {@code JsonSerializableCcaTracker}.
     */
    public JsonSerializableCcaTracker(CcaTracker ccaTracker) {
        ccas.addAll(ccaTracker.getCcaList().stream().map(JsonAdaptedCca::new).collect(Collectors.toList()));
    }

    /**
     * Converts this {@code JsonAdaptedCcaTracker} into the model's {@code CcaTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CcaTracker toModelType() throws IllegalValueException {
        CcaTracker ccaTracker = new CcaTracker();
        for (JsonAdaptedCca jsonAdaptedCca : ccas) {
            Cca cca = jsonAdaptedCca.toModelType();
            if (ccaTracker.containsCca(cca)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CCA);
            }
            ccaTracker.addCca(cca);
        }
        return ccaTracker;
    }
}

package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;

/**
 * Jackson-friendly version of {@link AutocorrectSuggestion}.
 */
public class JsonAdaptedAutocorrectSuggestion {

    private final String word;

    @JsonCreator
    public JsonAdaptedAutocorrectSuggestion(@JsonProperty("word") String word) {
        this.word = word;
    }

    /**
     * Converts a given {@code AutocorrectSuggestion} into this class for Jackson use.
     */
    public JsonAdaptedAutocorrectSuggestion(AutocorrectSuggestion source) {
        word = source.toString();
    }

    /**
     * Converts this Jackson-friendly adapted AutocorrectSuggestion object into the model's
     * {@code AutocorrectSuggestion} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public AutocorrectSuggestion toModelType() {
        return new AutocorrectSuggestion(word);
    }
}

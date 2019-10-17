package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;

public class JsonAdaptedAutocorrectSuggestion {

    private final String word;

    @JsonCreator
    public JsonAdaptedAutocorrectSuggestion(@JsonProperty("word") String word) {
        this.word = word;
    }

    public JsonAdaptedAutocorrectSuggestion(AutocorrectSuggestion source) {
        word = source.toString();
    }

    public AutocorrectSuggestion toModelType() throws IllegalValueException {
        return new AutocorrectSuggestion(word);
    }
}

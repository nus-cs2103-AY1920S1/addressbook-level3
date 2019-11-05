package seedu.address.testutil;

import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;

/**
 * Helps with building of Autosuggestion object
 */
public class AutocorrectSuggestionBuilder {

    public static final String DEFAULT_WORD = "add_claim n/Joshua";

    private String suggestion;

    public AutocorrectSuggestionBuilder() {
        suggestion = DEFAULT_WORD;
    }

    /**
     * Initializes the AutocorrectSuggestionBuilder with the data of {@code suggestionToCopy}.
     */
    public AutocorrectSuggestionBuilder(AutocorrectSuggestion suggestionToCopy) {
        suggestion = suggestionToCopy.getWord();
    }

    /**
     * Sets the {@code suggestion} of the {@code FinSec} that we are building.
     */
    public AutocorrectSuggestionBuilder withSuggestion(String suggestion) {
        this.suggestion = suggestion;
        return this;
    }

    public AutocorrectSuggestion build() {
        return new AutocorrectSuggestion(suggestion);
    }
}

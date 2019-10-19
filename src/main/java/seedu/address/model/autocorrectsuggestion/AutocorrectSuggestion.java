package seedu.address.model.autocorrectsuggestion;

/**
 * Represents a word that would be stored in the autocorrect suggestion list.
 */
public class AutocorrectSuggestion {

    private final String word;

    /**
     * Every field must be present and not null.
     */
    public AutocorrectSuggestion(String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    /**
     * Returns true if both autocorrectsuggestions have the same word.
     * This defines a stronger notion of equality between two autocorrectsuggestions.
     */
    public boolean isSameAutoCorrectionSuggestion(AutocorrectSuggestion other) {

        return other == this;

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AutocorrectSuggestion)) {
            return false;
        }
        AutocorrectSuggestion otherAutocorrectSuggestion = (AutocorrectSuggestion) other;
        return otherAutocorrectSuggestion.getWord().equals(getWord());
    }

    @Override
    public String toString() {
        return this.word;
    }


}

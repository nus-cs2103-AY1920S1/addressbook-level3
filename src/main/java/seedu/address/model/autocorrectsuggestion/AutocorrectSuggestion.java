package seedu.address.model.autocorrectsuggestion;

public class AutocorrectSuggestion {

    private final String word;

    public AutocorrectSuggestion(String word) {
        this.word = word;
    }

    public String getWord() { return this.word; }

    public boolean isSameAutoCorrectionSuggestion(AutocorrectSuggestion other) {

        return other == this;

    }

    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof AutocorrectSuggestion)) {
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

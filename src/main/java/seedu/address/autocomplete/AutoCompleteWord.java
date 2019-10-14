package seedu.address.autocomplete;

/**
 * Represents an autocomplete word in application
 */
public class AutoCompleteWord {
    private String suggestedWord;

    public AutoCompleteWord(String suggestedWord) {
        this.suggestedWord = suggestedWord;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }
}

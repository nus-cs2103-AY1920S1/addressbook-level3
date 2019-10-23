package seedu.address.autocomplete;

/**
 * Represents a prefix word(eg: n/, t/) in application
 */
public class PrefixWord extends AutoCompleteWord {
    private String associatedCommandWord;

    public PrefixWord(String associatedCommandWord, String suggestionWord) {
        super(suggestionWord);
        this.associatedCommandWord = associatedCommandWord;
    }

    public String getAssociatedCommandWord() {
        return associatedCommandWord;
    }
}

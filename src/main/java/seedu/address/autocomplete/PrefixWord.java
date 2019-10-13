package seedu.address.autocomplete;

public class PrefixWord extends AutoCompleteWord {
    String associatedCommandWord;

    public PrefixWord(String associatedCommandWord, String suggestionWord) {
        super(suggestionWord);
        this.associatedCommandWord = associatedCommandWord;
    }

    public String getAssociatedCommandWord() {
        return associatedCommandWord;
    }
}

package seedu.address.autocomplete;

/**
 * Represents an autocomplete command word(eg: find, sort, delete) in application
 */
public class CommandWord extends AutoCompleteWord {
    private String associatedObjectWord;
    public CommandWord(String associatedObjectWord, String suggestionWord) {
        super(suggestionWord);
        this.associatedObjectWord = associatedObjectWord;
    }

    public String getAssociatedObjectWord() {
        return associatedObjectWord;
    }
}

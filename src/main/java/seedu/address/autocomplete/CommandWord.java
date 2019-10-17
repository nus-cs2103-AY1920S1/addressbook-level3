package seedu.address.autocomplete;

/**
 * Represents an autocomplete command word(eg: find, sort, delete) in application
 */
public class CommandWord extends AutoCompleteWord {
    public CommandWord(String suggestionWord) {
        super(suggestionWord);
    }
}

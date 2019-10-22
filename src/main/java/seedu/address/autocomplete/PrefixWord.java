package seedu.address.autocomplete;

import java.util.LinkedList;

/**
 * Represents a prefix word(eg: n/, t/) in application
 */
public class PrefixWord extends AutoCompleteWord implements AssociableWord {
    private String associatedWord1;
    private String associatedWord2;

    public PrefixWord(String associatedWord1, String associatedWord2, String suggestionWord) {
        super(suggestionWord);
        this.associatedWord1 = associatedWord1;
        this.associatedWord2 = associatedWord2;
    }

    public LinkedList<String> getAssociatedWordList() {
        LinkedList<String> associatedWordList = new LinkedList<>();
        associatedWordList.add(associatedWord1);
        associatedWordList.add(associatedWord2);
        return associatedWordList;
    }
}

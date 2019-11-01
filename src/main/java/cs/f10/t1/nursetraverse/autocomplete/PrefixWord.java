package cs.f10.t1.nursetraverse.autocomplete;

import java.util.LinkedList;

/**
 * Represents a prefix word(eg: n/, t/) in application.
 */
public class PrefixWord extends AutoCompleteWord implements AssociableWord {
    // Two associated words is needed since the PrefixWord suggested will depend on both object and command word.
    private String associatedObjectWord;
    private String associatedCommandWord;

    public PrefixWord(String associatedObjectWord, String associatedCommandWord, String suggestedPrefix) {
        super(suggestedPrefix);
        this.associatedCommandWord = associatedCommandWord;
        this.associatedObjectWord = associatedObjectWord;
    }

    public LinkedList<String> getAssociatedWordList() {
        LinkedList<String> associatedWordList = new LinkedList<>();
        associatedWordList.add(associatedObjectWord);
        associatedWordList.add(associatedCommandWord);
        return associatedWordList;
    }

    @Override
    public String getConnectorChar() {
        return " ";
    }
}

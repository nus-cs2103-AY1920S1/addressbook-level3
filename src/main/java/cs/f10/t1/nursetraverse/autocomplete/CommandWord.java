package cs.f10.t1.nursetraverse.autocomplete;

import java.util.LinkedList;

/**
 * Represents an autocomplete command word(eg: find, sort, delete) in application
 */
public class CommandWord extends AutoCompleteWord implements AssociableWord {
    private String associatedWord;
    private boolean hasIndex;
    private boolean hasPrefix;

    public CommandWord(String associatedWord, String suggestionWord, Boolean hasIndex , Boolean hasPrefix) {
        super(suggestionWord);
        this.associatedWord = associatedWord;
        this.hasIndex = hasIndex;
        this.hasPrefix = hasPrefix;
    }

    public LinkedList<String> getAssociatedWordList() {
        LinkedList<String> associatedWordList = new LinkedList<>();
        associatedWordList.add(associatedWord);
        return associatedWordList;
    }

    public boolean hasIndex() {
        return hasIndex;
    }

    public boolean hasPrefix() {
        return hasPrefix;
    }

    @Override
    public String getConnectorChar() {
        return " ";
    }
}

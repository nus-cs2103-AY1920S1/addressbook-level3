package cs.f10.t1.nursetraverse.autocomplete;

/**
 * Represents an autocomplete word in application
 */
public abstract class AutoCompleteWord {
    private String suggestedWord;

    public AutoCompleteWord(String suggestedWord) {
        this.suggestedWord = suggestedWord;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }

    public abstract String getConnectorChar();
}

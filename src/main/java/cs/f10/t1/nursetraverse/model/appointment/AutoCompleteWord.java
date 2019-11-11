package cs.f10.t1.nursetraverse.model.appointment;

/**
 * Represents an autocomplete word in application
 */
public abstract class AutoCompleteWord {
    private String suggestedWord;
    private String description;

    public AutoCompleteWord(String suggestedWord, String description) {
        this.suggestedWord = suggestedWord;
        this.description = description;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getConnectorChar();
}

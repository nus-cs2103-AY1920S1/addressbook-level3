package seedu.address.autocomplete;

public class AutoCompleteWord {
    private String suggestedWord;

    public AutoCompleteWord(String suggestedWord) {
        this.suggestedWord = suggestedWord;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }
}

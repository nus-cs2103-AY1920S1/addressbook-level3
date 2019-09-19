package seedu.address.logic.autocomplete;

import java.util.ArrayList;

public class AutoCompleter {
    private Trie trie = new Trie();
    private String currentQuery;

    public void update(String currentQuery) {
        this.currentQuery = currentQuery;
    }

    public ArrayList<String> getSuggestions() {
        return trie.find(currentQuery).getPossibilities();
    }
}

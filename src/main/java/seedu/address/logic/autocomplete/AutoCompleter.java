package seedu.address.logic.autocomplete;

import java.util.Collections;
import java.util.List;

/**
 * Logic Component for AutoComplete
 */
public class AutoCompleter {
    private static final String[] SUPPORTED_COMMANDS = {
            "ackappt",
            "addappt",
            "add",
            "appointments",
            "cancel",
            "change",
            "dequeue",
            "doctors",
            "exit",
            "help",
            "missed",
            "next",
            "offduty",
            "onduty",
            "patient",
            "queue",
            "register",
            "settle",
            "slot",
            "update",
            "undo",
            "cancelappt",
            "changeAppt"
    };
    private Trie trie = new Trie(SUPPORTED_COMMANDS);
    private String currentQuery;

    /**
     * Updates AutoCompleter with current query.
     *
     * @param currentQuery
     * @return AutoCompleter itself
     */
    public AutoCompleter update(String currentQuery) {
        this.currentQuery = currentQuery;
        return this;
    }

    public List<String> getSuggestions() {
        try {
            return trie.find(currentQuery).getPossibilities();
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
    }
}

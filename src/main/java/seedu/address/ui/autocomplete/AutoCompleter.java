package seedu.address.ui.autocomplete;

import java.util.Collections;
import java.util.List;

/**
 * Component for AutoComplete
 */
public class AutoCompleter {
    private static final String[] SUPPORTED_COMMANDS = {
            "ackappt",
            "addappt",
            "add",
            "addRoom",
            "appointments",
            "break",
            "cancel",
            "changeappt",
            "dequeue",
            "doctors",
            "exit",
            "help",
            "next",
            "missappt",
            "enqueue",
            "offduty",
            "onduty",
            "patient",
            "queue",
            "register",
            "removeRoom",
            "resume",
            "settleappt",
            "update",
            "undo",
            "changeappt",
            "cancelappt",
            "hiredoctor",
            "resigndoctor"
    };
    private Trie trie = new Trie(SUPPORTED_COMMANDS);
    private String currentQuery;

    /**
     * Updates AutoComplete with current query.
     *
     * @param currentQuery
     * @return AutoComplete itself
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

package seedu.address.ui.autocomplete;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Component for AutoComplete
 */
public class AutoCompleter {
    private static final Map<String, HashSet> SUPPORTED_ARGUMENTS = Map.ofEntries(
        Map.entry("add", new HashSet(Arrays.asList("-name", "-id")))
    );

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
        "cancelappt"
    };

    private Trie trie;
    private String currentQuery;

    public AutoCompleter() {
        trie = new Trie(SUPPORTED_COMMANDS);
    }

    public AutoCompleter(String... arr) {
        this.trie = new Trie(arr);
    }

    /**
     * Updates AutoComplete with current query.
     *
     * @param currentQuery
     * @return AutoComplete itself
     */
    public AutoCompleter update(String currentQuery) {
        if (currentQuery.contains(" ")) {
            try {
                HashSet<String> available = (HashSet) SUPPORTED_ARGUMENTS.get(currentQuery.substring(0,
                    currentQuery.indexOf(' ')))
                    .clone();
                available.removeAll(Arrays.asList(currentQuery.split("\\s+")));
                AutoCompleter autoCompleter = new AutoCompleter(available.toArray(new String[0]));
                autoCompleter.currentQuery = currentQuery.substring(currentQuery.lastIndexOf(' ') + 1);
                return autoCompleter;
            } catch (NullPointerException e) {
                return new AutoCompleter("");
            }
        } else {
            this.currentQuery = currentQuery;
            return this;
        }
    }

    public List<String> getSuggestions() {
        try {
            return trie.find(currentQuery).getPossibilities();
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
    }
}

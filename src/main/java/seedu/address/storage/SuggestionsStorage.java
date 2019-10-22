package seedu.address.storage;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;

/**
 * Stores the list of suggestions to be displayed for command box.
 */
public class SuggestionsStorage {
    private static SortedSet<String> suggestions = new TreeSet<String>();


    public static void setSuggestionList(ObservableList<AutocorrectSuggestion> list) {
        suggestions.clear();
        suggestions.addAll(list.stream().map(x->x.getWord()).collect(Collectors.toSet()));
    }

    /**
     * Returns the static SortedSet of suggestions.
     * @return the SortedSet.
     */
    public static SortedSet<String> getSuggestions() {
        return suggestions;
    }

}

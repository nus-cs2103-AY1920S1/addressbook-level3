package seedu.address.storage;

import java.util.HashSet;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;

/**
 * Stores the list of suggestions to be displayed for command box.
 */
public class SuggestionsStorage {
    private static HashSet<String> suggestions = new HashSet<>();


    public static void setSuggestionList(ObservableList<AutocorrectSuggestion> list) {
        suggestions.clear();
        suggestions.addAll(list.stream().map(x->x.getWord()).collect(Collectors.toSet()));
    }

    /**
     * Returns the static Set of suggestions.
     * @return the SortedSet.
     */
    public static HashSet<String> getSuggestions() {
        return suggestions;
    }

}

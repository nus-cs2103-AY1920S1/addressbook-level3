package seedu.address.storage;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.contact.Contact;

/**
 * Stores the list of suggestions to be displayed for command box.
 */
public class SuggestionsStorage {
    private static SortedSet<String> suggestions = new TreeSet<String>();

    /**
     * Adds details of contact to the treeset of suggestions.
     * @param contact The details of the particular contact to be added.
     */
    public static void addContactDetailsToSuggestionList(Contact contact) {
        suggestions.add(contact.getName().toString());
        suggestions.add(contact.getPhone().toString());
        suggestions.add(contact.getEmail().toString());
        suggestions.add(contact.getAddress().toString());
    }

    public static void setSuggestionList(ObservableList<AutocorrectSuggestion> list) {
        suggestions.clear();
        suggestions.addAll(list.stream().map(x->x.getWord()).collect(Collectors.toSet()));
    }
    /**
     * Deletes details of contact from the treeset of suggestions
     * @param contact
     */
    public static void deleteContactDetailsFromSuggestionList(Contact contact) {
        suggestions.remove(contact.getName().toString());
        suggestions.remove(contact.getPhone().toString());
        suggestions.remove(contact.getEmail().toString());
        suggestions.remove(contact.getAddress().toString());
    }

    /**
     * Returns the static SortedSet of suggestions.
     * @return the SortedSet.
     */
    public static SortedSet<String> getSuggestions() {
        return suggestions;
    }

}

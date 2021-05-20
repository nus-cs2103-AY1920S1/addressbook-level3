package seedu.address.model.autocorrectsuggestion;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of suggestions that enforces uniqueness between its elements and does not allow nulls.
 * An autocorrectSuggestion is considered unique by comparing using
 * {@code AutocorrectSuggestion#isSameAutoCorrectionSuggestion(AutocorrectSuggestion)}. As such, adding and updating
 * of autocorrectsuggestion uses it for equality so as to ensure that the autocorrectsuggestion being added or updated
 * is unique in terms of identity in the UniqueAutocorrectSuggestionList. However, the removal of an income uses
 * AutocorrectSuggestion#equals(Object) so as to ensure that the suggestion will be removed.
 *
 *
 * @see AutocorrectSuggestion#isSameAutoCorrectionSuggestion(AutocorrectSuggestion)
 * */
public class UniqueAutocorrectSuggestionList implements Iterable<AutocorrectSuggestion> {

    private final ObservableList<AutocorrectSuggestion> internalList = FXCollections.observableArrayList();
    private final ObservableList<AutocorrectSuggestion> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent AutocorrectSuggestion as the given argument.
     */
    public boolean contains(AutocorrectSuggestion toCheck) {
        return internalList.stream().anyMatch(toCheck::isSameAutoCorrectionSuggestion);
    }

    /**
     * Adds an autocorrectSuggestion to the list.
     * It must not already exist in the list.
     */
    public void add(AutocorrectSuggestion toAdd) {
        internalList.add(toAdd);
    }

    /**
     * Replaces the AutocorrectSuggestion {@code target} in the list with {@code editedSuggestion}.
     * {@code target} must exist in the list.
     * The autocorrectSuggestion identity of {@code editedSuggestion} must not be the same as another existing
     * autocorrectSuggestion in the list.
     */
    public void setAutocorrectSuggestion(AutocorrectSuggestion target, AutocorrectSuggestion editedSuggestion) {

        int index = internalList.indexOf(target);
        internalList.set(index, editedSuggestion);
    }

    /**
     * Removes the equivalent autocorrectSuggestion from the list.
     * The autocorrectSuggestion must exist in the list.
     */
    public void remove(AutocorrectSuggestion toRemove) {
        internalList.remove(toRemove);
    }

    public void setAutocorrectSuggestions(UniqueAutocorrectSuggestionList replacement) {
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code listOfSuggestions}.
     */
    public void setAutocorrectSuggestions(List<AutocorrectSuggestion> listOfSuggestions) {
        if (autocorrectSuggestionsAreUnique(listOfSuggestions)) {
            internalList.setAll(listOfSuggestions);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<AutocorrectSuggestion> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<AutocorrectSuggestion> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AutocorrectSuggestion // instanceof handles nulls
                && internalList.equals(((UniqueAutocorrectSuggestionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    /**
     * Returns true if {@code suggestions} contains only unique autocorrectSuggestions.
     */
    private boolean autocorrectSuggestionsAreUnique(List<AutocorrectSuggestion> suggestions) {
        for (int i = 0; i < suggestions.size() - 1; i++) {
            for (int j = i + 1; j < suggestions.size(); j++) {
                if (suggestions.get(i).isSameAutoCorrectionSuggestion(suggestions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

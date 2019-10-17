package seedu.address.model.autocorrectsuggestion;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniqueAutocorrectSuggestionList implements  Iterable<AutocorrectSuggestion> {

    private final ObservableList<AutocorrectSuggestion> internalList = FXCollections.observableArrayList();
    private final ObservableList<AutocorrectSuggestion> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(AutocorrectSuggestion toCheck) {
        return internalList.stream().anyMatch(toCheck::isSameAutoCorrectionSuggestion);
    }

    public void add(AutocorrectSuggestion toAdd) {
        internalList.add(toAdd);
    }

    public void setAutocorrectSuggestion(AutocorrectSuggestion target, AutocorrectSuggestion editedSuggestion) {

        int index = internalList.indexOf(target);
        internalList.set(index, editedSuggestion);
    }

    public void remove(AutocorrectSuggestion toRemove) {
        internalList.remove(toRemove);
    }

    public void setAutocorrectSuggestions(UniqueAutocorrectSuggestionList replacement) {
        internalList.setAll(replacement.internalList);
    }

    public void setAutocorrectSuggestions(List<AutocorrectSuggestion> listOfSuggestions) {
        if(autocorrectSuggestionsAreUnique(listOfSuggestions)) {
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

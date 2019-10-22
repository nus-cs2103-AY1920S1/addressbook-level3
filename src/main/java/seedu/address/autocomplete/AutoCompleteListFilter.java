package seedu.address.autocomplete;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/**
 * Filter out autocomplete words in the current list that are unrelated to the already matched words
 */
public class AutoCompleteListFilter {
    // Only need to filter command words list and prefix word list
    public ObservableList<AutoCompleteWord> filterList(LinkedList<AutoCompleteWord> correctWords, ObservableList<AutoCompleteWord> listToBeSuggested) {
        ObservableList<AutoCompleteWord> filteredList = FXCollections.observableArrayList();
        // filter based on first 2 correct word
        for (AutoCompleteWord autoCompleteWord : listToBeSuggested) {
            boolean isRelated = true;
            if (correctWords.size() >= 1) {
                if (!correctWords.get(0).getSuggestedWord().equals(((AssociableWord) autoCompleteWord).getAssociatedWordList().get(0))) {
                    isRelated = false;
                }
            }
            if (correctWords.size() >= 2) {
                if (!correctWords.get(1).getSuggestedWord().equals(((AssociableWord) autoCompleteWord).getAssociatedWordList().get(1))) {
                    isRelated = false;
                }
            }
            if (isRelated) {
                filteredList.add(autoCompleteWord);
            }
        }
        return filteredList;
    }
}

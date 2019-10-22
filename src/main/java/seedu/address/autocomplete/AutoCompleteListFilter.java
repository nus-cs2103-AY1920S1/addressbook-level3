package seedu.address.autocomplete;

import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Filter out autocomplete words in the current list that are unrelated to the already matched words
 */
public class AutoCompleteListFilter {
    /**
     * Filter unrelated words from either prefix or command wordlist
     *
     * @param matchedWords linkedlist of current matched words
     * @param listToBeSuggested list to be filtered
     * @return
     */
    public ObservableList<AutoCompleteWord> filterList(LinkedList<AutoCompleteWord> matchedWords,
                                                       ObservableList<AutoCompleteWord> listToBeSuggested) {
        ObservableList<AutoCompleteWord> filteredList = FXCollections.observableArrayList();
        // filter based on first 2 correct word
        for (AutoCompleteWord autoCompleteWord : listToBeSuggested) {
            boolean isRelated = true;
            if (matchedWords.size() >= 1) {
                if (!matchedWords.get(0).getSuggestedWord().equals(((AssociableWord) autoCompleteWord)
                        .getAssociatedWordList().get(0))) {
                    isRelated = false;
                }
            }
            if (matchedWords.size() >= 2) {
                if (!matchedWords.get(1).getSuggestedWord().equals(((AssociableWord) autoCompleteWord)
                        .getAssociatedWordList().get(1))) {
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

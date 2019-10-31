package cs.f10.t1.nursetraverse.autocomplete;

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
     * @param matchedWords      linkedlist of current matched words
     * @param listToBeSuggested list to be filtered
     * @return
     */
    public ObservableList<AutoCompleteWord> filterList(LinkedList<AutoCompleteWord> matchedWords,
                                                       ObservableList<AutoCompleteWord> listToBeSuggested) {
        ObservableList<AutoCompleteWord> filteredList = FXCollections.observableArrayList();
        // filter based on first 2 correct word
        for (AutoCompleteWord autoCompleteWord : listToBeSuggested) {
            boolean isAssociable = true;

            // Checks what if word in list are associable to the correctly matched words

            // Checks for first match word
            if (matchedWords.size() >= 1) {
                boolean isAssociableToFirstWord = matchedWords.get(0).getSuggestedWord()
                        .equals(((AssociableWord) autoCompleteWord).getAssociatedWordList().get(0));
                if (!isAssociableToFirstWord) {
                    isAssociable = false;
                }
            }
            // Checks for second match word
            if (matchedWords.size() >= 2) {
                boolean isAssociableToSecondWord = matchedWords.get(1).getSuggestedWord()
                        .equals(((AssociableWord) autoCompleteWord).getAssociatedWordList().get(1));
                if (!isAssociableToSecondWord) {
                    isAssociable = false;
                }
            }
            if (isAssociable) {
                filteredList.add(autoCompleteWord);
            }
        }
        return filteredList;
    }
}

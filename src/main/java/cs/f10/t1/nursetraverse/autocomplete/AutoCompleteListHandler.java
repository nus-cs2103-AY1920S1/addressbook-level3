package cs.f10.t1.nursetraverse.autocomplete;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * A handler class that updates list of autocomplete words to be shown according to userinput
 */
public class AutoCompleteListHandler {
    /**
     * Update list of autocomplete words to be suggested according to current phrase in command box textfield
     *
     * @param currentList list to be updated
     * @param numberOfMatchedWords number of matched words in command textfield
     * @param segments Array of segments of the full command in textfield
     * @param firstSegmentParts Linkedlist of parts in first segment of segments array
     * @return updatedList
     */
    public ObservableList<AutoCompleteWord> updateList(ObservableList<AutoCompleteWord> currentList,
                                                       int numberOfMatchedWords, String[] segments,
                                                       LinkedList<String> firstSegmentParts) {
        ObservableList<AutoCompleteWord> updatedList = FXCollections.observableArrayList();

        LinkedList<String> combinedList = new LinkedList<>(firstSegmentParts);
        combinedList.addAll(Arrays.asList(segments).subList(1, segments.length));

        if (numberOfMatchedWords == combinedList.size()) {
            return currentList;
        } else {
            for (AutoCompleteWord autoCompleteWord : currentList) {
                if (autoCompleteWord.getSuggestedWord().startsWith(combinedList.get(numberOfMatchedWords))) {
                    updatedList.add(autoCompleteWord);
                }
            }
            return updatedList;
        }
    }

    /**
     * Filter unrelated words from either prefix or command wordlist
     *
     * @param matchedWords linkedlist of current matched words
     * @param listToBeSuggested list to be filtered
     * @return filteredList
     */
    public ObservableList<AutoCompleteWord> filterList(LinkedList<AutoCompleteWord> matchedWords,
                                                       ObservableList<AutoCompleteWord> listToBeSuggested) {
        ObservableList<AutoCompleteWord> filteredList = FXCollections.observableArrayList();
        // Perform filter only if listToBeSuggested is not empty and is a prefix or command list
        if (listToBeSuggested.size() != 0 && (listToBeSuggested.get(0) instanceof PrefixWord
                || listToBeSuggested.get(0) instanceof CommandWord)) {
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
        else {
            return listToBeSuggested;
        }
    }
}

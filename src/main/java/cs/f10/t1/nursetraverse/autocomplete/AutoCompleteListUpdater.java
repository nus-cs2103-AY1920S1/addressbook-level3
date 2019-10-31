package cs.f10.t1.nursetraverse.autocomplete;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A handler class that updates list of autocomplete words to be shown according to userinput
 */
public class AutoCompleteListUpdater {
    /**
     * Update list of autocomplete words to be suggested according to current phrase in command box textfield
     *
     * @param numberOfMatchedWords number of matched words in command textfield
     * @param segments Array of segments of the full command in textfield
     * @param firstSegmentParts Linkedlist of parts in first segment of segments array
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
}

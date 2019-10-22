package seedu.address.autocomplete;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * A handler that initialises autocomplete word bank and updates list of autocomplete words to be shown
 */
public class AutoCompleteListUpdater {
    private ObservableList<AutoCompleteWord> oListSuggestedWords;
    private ObservableList<AutoCompleteWord> oListSuggestedWordsCopy;

    public void setList(ObservableList<AutoCompleteWord> oListSuggestedWords) {
        this.oListSuggestedWords = oListSuggestedWords;
        this.oListSuggestedWordsCopy = FXCollections.observableArrayList(oListSuggestedWords);
    }

    /**
     * Update list of autocomplete words to be suggested according to current phrase in command box textfield
     *
     * @param currentPhraseInCommandBox string in command box textfield
     */
    public void updateSuggestedWordsInList(int numberOfMatchedWords, String currentPhraseInCommandBox) {
        oListSuggestedWords.clear();
        String[] segments = UserinputParserUtil.splitIntoSegments(currentPhraseInCommandBox);
        LinkedList<String> firstSegmentParts = UserinputParserUtil.parseFirstSegment(segments[0]);

        LinkedList<String> combinedList = new LinkedList<>(firstSegmentParts);
        combinedList.addAll(Arrays.asList(segments).subList(1, segments.length));

        if (numberOfMatchedWords == combinedList.size()) {
            oListSuggestedWords.addAll(oListSuggestedWordsCopy);
        } else {
            for (AutoCompleteWord autoCompleteWord : oListSuggestedWordsCopy) {
                if (autoCompleteWord.getSuggestedWord().startsWith(combinedList.get(numberOfMatchedWords))) {
                    oListSuggestedWords.add(autoCompleteWord);
                }
            }
        }
    }

    public ObservableList<AutoCompleteWord> getOListSuggestedWords() {
        return oListSuggestedWords;
    }
}

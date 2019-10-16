package seedu.address.autocomplete;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;

/**
 * A handler that initialises autocomplete word bank and updates list of autocomplete words to be shown
 */
public class AutoCompleteWordHandler {
    private static final Logger logger = LogsCenter.getLogger(AutoCompleteWordHandler.class);

    private ObservableList<AutoCompleteWord> oListSuggestedWords;
    private ObservableList<AutoCompleteWord> oListAllWords;

    public AutoCompleteWordHandler(ObservableList<AutoCompleteWord> oListAllWords) {
        // Init list suggestible words
        setList(oListAllWords);
    }

    public void setList(ObservableList<AutoCompleteWord> oListAllWords) {
        this.oListAllWords = oListAllWords;
        this.oListSuggestedWords = FXCollections.observableArrayList(oListAllWords);
    }

    /**
     * Update list of autocomplete words to be suggested according to current phrase in command box textfield
     *
     * @param currentPhraseInCommandBox string in command box textfield
     */
    public void updateSuggestedWordsInList(String currentPhraseInCommandBox) {
        oListSuggestedWords.clear();
        String[] userinputs = currentPhraseInCommandBox.split(" ");

        for (AutoCompleteWord autoCompleteWord : oListAllWords) {
            if (autoCompleteWord.getSuggestedWord().startsWith(userinputs[userinputs.length - 1])) {
                oListSuggestedWords.add(autoCompleteWord);
            }
        }
        logger.info("Current number of suggested word is " + oListSuggestedWords.size());
    }

    public ObservableList<AutoCompleteWord> getOListSuggestedWords() {
        return oListSuggestedWords;
    }
}

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

    private ObservableList<AutoCompleteWord> oListAutoCompleteWords;
    private ObservableList<AutoCompleteWord> oListAutoCompleteWordBank;


    public AutoCompleteWordHandler() {
        this.oListAutoCompleteWordBank = FXCollections.observableArrayList();

        // Add command suggestion words
        addCommandWordsToBank();

        /*// Add variable suggestion words
        addPrefixToBank();*/


        this.oListAutoCompleteWords = FXCollections.observableArrayList(oListAutoCompleteWordBank);
    }

    /**
     * Add command words to list of all suggested words
     */
    public void addCommandWordsToBank() {
        oListAutoCompleteWordBank.add(new CommandWord("list"));
        oListAutoCompleteWordBank.add(new CommandWord("find"));
        oListAutoCompleteWordBank.add(new CommandWord("delete"));
        oListAutoCompleteWordBank.add(new CommandWord("add"));
        oListAutoCompleteWordBank.add(new CommandWord("import"));
        oListAutoCompleteWordBank.add(new CommandWord("history"));
    }

    /*public void addPrefixToBank() {
        oListACWordBank.add(new PrefixWord("import", "r/"));
        oListACWordBank.add(new PrefixWord("import", "c/"));

    }*/

    public ObservableList<AutoCompleteWord> getOListAutoCompleteWords() {
        return oListAutoCompleteWords;
    }

    /**
     * Update list of autocomplete words to be suggested according to current phrase in command box textfield
     * @param currentPhraseInCommandBox string in command box textfield
     */
    public void updateList(String currentPhraseInCommandBox) {
        oListAutoCompleteWords.clear();

        for (AutoCompleteWord autoCompleteWord : oListAutoCompleteWordBank) {
            if (autoCompleteWord.getSuggestedWord().contains(currentPhraseInCommandBox)) {
                oListAutoCompleteWords.add(autoCompleteWord);
            }
        }
        logger.info("Current number of suggested word is " + oListAutoCompleteWords.size());
    }
}

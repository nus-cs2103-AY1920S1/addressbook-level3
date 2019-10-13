package seedu.address.autocomplete;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AutoCompleteWordHandler {
    private ObservableList<AutoCompleteWord> oListACWords;
    private ObservableList<AutoCompleteWord> oListACWordBank;


    public AutoCompleteWordHandler() {
        this.oListACWordBank = FXCollections.observableArrayList();

        // Add command suggestion words
        addCommandWordsToBank();

        /*// Add variable suggestion words
        addPrefixToBank();*/


        this.oListACWords = FXCollections.observableArrayList(oListACWordBank);
    }

    public void addCommandWordsToBank() {
        oListACWordBank.add(new CommandWord("list"));
        oListACWordBank.add(new CommandWord("find"));
        oListACWordBank.add(new CommandWord("delete"));
        oListACWordBank.add(new CommandWord("add"));
        oListACWordBank.add(new CommandWord("import"));
    }

    /*public void addPrefixToBank() {
        oListACWordBank.add(new PrefixWord("import", "r/"));
        oListACWordBank.add(new PrefixWord("import", "c/"));

    }*/

    public ObservableList<AutoCompleteWord> getOListOfSuggestionWords() {
        return oListACWords;
    }

    public void updateList(String currentPhraseInCommandBox) {
        oListACWords.clear();
        System.out.println(oListACWords.size());
        System.out.println(oListACWordBank.size());

        for (AutoCompleteWord autoCompleteWord : oListACWordBank) {
            if (autoCompleteWord.getSuggestedWord().contains(currentPhraseInCommandBox)) {
                oListACWords.add(autoCompleteWord);
            }
        }
    }
}

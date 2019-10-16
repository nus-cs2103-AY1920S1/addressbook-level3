package seedu.address.autocomplete;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AutoCompleteWordStorage {
    private ObservableList<AutoCompleteWord> oListAllObjectWord;
    private ObservableList<AutoCompleteWord> oListAllCommandWord;
    private ObservableList<AutoCompleteWord> oListAllPrefixWord;


    public AutoCompleteWordStorage() {
        this.oListAllCommandWord = initAllCommandWordList();
        this.oListAllPrefixWord = initAllPrefixWordList();
        this.oListAllObjectWord = initAllObjectWordList();
    }

    /**
     * Add command words to list of all suggestible command words
     */
    private ObservableList<AutoCompleteWord> initAllCommandWordList() {
        ObservableList<AutoCompleteWord> oListAllCommandWord = FXCollections.observableArrayList();
        oListAllCommandWord.add(new CommandWord("patient","list"));
        oListAllCommandWord.add(new CommandWord("patient","find"));
        oListAllCommandWord.add(new CommandWord("patient","delete"));
        oListAllCommandWord.add(new CommandWord("patient","add"));
        oListAllCommandWord.add(new CommandWord("patient","addmedcon"));

        oListAllCommandWord.add(new CommandWord("medcon","add"));

        oListAllCommandWord.add(new CommandWord("med","add"));

        return oListAllCommandWord;
    }

    /**
     * Add prefix words to list of all suggestible prefix words
     */
    private ObservableList<AutoCompleteWord> initAllPrefixWordList() {
        ObservableList<AutoCompleteWord> oListAllPrefixWord = FXCollections.observableArrayList();
        oListAllPrefixWord.add(new PrefixWord("add", "r/"));
        oListAllPrefixWord.add(new PrefixWord("find", "c/"));

        return oListAllPrefixWord;
    }

    /**
     * Add prefix words to list of all suggestible prefix words
     */
    private ObservableList<AutoCompleteWord> initAllObjectWordList() {
        ObservableList<AutoCompleteWord> oListAllObjectWord = FXCollections.observableArrayList();
        oListAllObjectWord.add(new ObjectWord("patient"));
        oListAllObjectWord.add(new ObjectWord("med"));
        oListAllObjectWord.add(new ObjectWord("medcon"));

        return oListAllObjectWord;
    }

    public ObservableList<AutoCompleteWord> getOListAllCommandWord() {
        return oListAllCommandWord;
    }

    public ObservableList<AutoCompleteWord> getOListAllPrefixWord() {
        return oListAllPrefixWord;
    }

    public ObservableList<AutoCompleteWord> getOListAllObjectWord() {
        return oListAllObjectWord;
    }
}

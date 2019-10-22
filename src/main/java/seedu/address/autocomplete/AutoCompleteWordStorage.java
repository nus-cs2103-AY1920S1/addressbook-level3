package seedu.address.autocomplete;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;

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
        oListAllCommandWord.add(new CommandWord("visit", "end", false, false));
        oListAllCommandWord.add(new CommandWord("visit", "start", false, true));
        oListAllCommandWord.add(new CommandWord("visit", "now-show", false, false));
        oListAllCommandWord.add(new CommandWord("visit", "now-update", false, true));



        oListAllCommandWord.add(new CommandWord("patient", "list", false, false));
        oListAllCommandWord.add(new CommandWord("patient", "find", false, true));
        oListAllCommandWord.add(new CommandWord("patient", "view", true, false));
        oListAllCommandWord.add(new CommandWord("patient", "add-medcon", false, true));
        oListAllCommandWord.add(new CommandWord("app","undo", false, false));
        oListAllCommandWord.add(new CommandWord("app", "redo", false, false));
        oListAllCommandWord.add(new CommandWord("app", "import-replace", false, true));
        oListAllCommandWord.add(new CommandWord("app", "export-all", false, false));
        oListAllCommandWord.add(new CommandWord("app", "help", false, false));
        oListAllCommandWord.add(new CommandWord("medcon", "delete", true, false));
        oListAllCommandWord.add(new CommandWord("med", "add", false, true));
        oListAllCommandWord.add(new CommandWord("appt", "edit", true, true));

        return oListAllCommandWord;
    }

    /**
     * Add prefix words to list of all suggestible prefix words
     */
    private ObservableList<AutoCompleteWord> initAllPrefixWordList() {
        ObservableList<AutoCompleteWord> oListAllPrefixWord = FXCollections.observableArrayList();
        oListAllPrefixWord.add(new PrefixWord("visit", "start", "p/"));
        oListAllPrefixWord.add(new PrefixWord("visit", "now-update", "t/"));
        oListAllPrefixWord.add(new PrefixWord("visit", "now-update", "d/"));
        oListAllPrefixWord.add(new PrefixWord("visit", "now-update", "ud/"));
        oListAllPrefixWord.add(new PrefixWord("visit", "now-update", "r/"));




        oListAllPrefixWord.add(new PrefixWord("patient", "add-medcon", "t/"));
        oListAllPrefixWord.add(new PrefixWord("patient", "find", "t/"));
        oListAllPrefixWord.add(new PrefixWord("medcon", "find", "c/"));
        oListAllPrefixWord.add(new PrefixWord("patient", "add-medcon", "c/"));
        oListAllPrefixWord.add(new PrefixWord("appt", "edit", "c/"));

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
        oListAllObjectWord.add(new ObjectWord("app"));
        oListAllObjectWord.add(new ObjectWord("visit"));
        oListAllObjectWord.add(new ObjectWord("appt"));

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

    // total person/medcon etc may change during runtime (to improve later)
    public ObservableList<AutoCompleteWord> generateOListAllIndexWord() {
        ObservableList<AutoCompleteWord> oListAllIndexWord = FXCollections.observableArrayList();
        oListAllIndexWord.add(new IndexWord("1"));
        oListAllIndexWord.add(new IndexWord("2"));
        oListAllIndexWord.add(new IndexWord("3"));
        return oListAllIndexWord;
    }
}

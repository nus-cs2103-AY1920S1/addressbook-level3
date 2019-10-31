package cs.f10.t1.nursetraverse.autocomplete;

import cs.f10.t1.nursetraverse.logic.commands.EditCommand;
import cs.f10.t1.nursetraverse.logic.commands.FindCommand;
import cs.f10.t1.nursetraverse.logic.commands.HelpCommand;
import cs.f10.t1.nursetraverse.logic.commands.ImportReplaceCommand;
import cs.f10.t1.nursetraverse.logic.commands.ListCommand;
import cs.f10.t1.nursetraverse.logic.commands.RedoCommand;
import cs.f10.t1.nursetraverse.logic.commands.UndoCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.CancelOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.FinishOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.UpdateOngoingVisitCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that initialise and stores all list
 */
public class AutoCompleteWordStorage {
    public static final String VISITOBJECTWORD = "visit-";
    public static final String PATIENTOBJECTWORD = "pat-";
    public static final String APPLICATIONOBJECTWORD = "app-";
    public static final String APPOINTMENTBJECTWORD = "appt-";

    private ObservableList<AutoCompleteWord> oListAllObjectWord;
    private ObservableList<AutoCompleteWord> oListAllCommandWord;
    private ObservableList<AutoCompleteWord> oListAllPrefixWord;

    public AutoCompleteWordStorage() {
        this.oListAllCommandWord = initAllCommandWordList();
        this.oListAllPrefixWord = initAllPrefixWordList();
        this.oListAllObjectWord = initAllObjectWordList();
    }

    /**
     * Initialise command list
     */
    private ObservableList<AutoCompleteWord> initAllCommandWordList() {
        ObservableList<AutoCompleteWord> oListAllCommandWord = FXCollections.observableArrayList();
        oListAllCommandWord.add(new CommandWord(VISITOBJECTWORD, BeginVisitCommand.COMMAND_WORD,
                false, false));
        oListAllCommandWord.add(new CommandWord(VISITOBJECTWORD, FinishOngoingVisitCommand.COMMAND_WORD,
                false, true));
        oListAllCommandWord.add(new CommandWord(VISITOBJECTWORD, CancelOngoingVisitCommand.COMMAND_WORD,
                false, false));
        oListAllCommandWord.add(new CommandWord(VISITOBJECTWORD, UpdateOngoingVisitCommand.COMMAND_WORD,
                false, true));

        oListAllCommandWord.add(new CommandWord(PATIENTOBJECTWORD, ListCommand.COMMAND_WORD, false, false));
        oListAllCommandWord.add(new CommandWord(PATIENTOBJECTWORD, FindCommand.COMMAND_WORD, false, true));
        oListAllCommandWord.add(new CommandWord(APPLICATIONOBJECTWORD, UndoCommand.COMMAND_WORD, false, false));
        oListAllCommandWord.add(new CommandWord(APPLICATIONOBJECTWORD, RedoCommand.COMMAND_WORD, false, false));
        oListAllCommandWord.add(new CommandWord(APPLICATIONOBJECTWORD, ImportReplaceCommand.COMMAND_WORD, false, true));
        oListAllCommandWord.add(new CommandWord(APPLICATIONOBJECTWORD, "export-all", false, false));
        oListAllCommandWord.add(new CommandWord(APPLICATIONOBJECTWORD, HelpCommand.COMMAND_WORD, false, false));
        oListAllCommandWord.add(new CommandWord(APPOINTMENTBJECTWORD, EditCommand.COMMAND_WORD, true, true));

        return oListAllCommandWord;
    }

    /**
     * Initialise prefix list
     */
    private ObservableList<AutoCompleteWord> initAllPrefixWordList() {
        ObservableList<AutoCompleteWord> oListAllPrefixWord = FXCollections.observableArrayList();
        oListAllPrefixWord.add(new PrefixWord(VISITOBJECTWORD, BeginVisitCommand.COMMAND_WORD, "p/"));
        oListAllPrefixWord.add(new PrefixWord(VISITOBJECTWORD, UpdateOngoingVisitCommand.COMMAND_WORD, "t/"));
        oListAllPrefixWord.add(new PrefixWord(VISITOBJECTWORD, UpdateOngoingVisitCommand.COMMAND_WORD, "d/"));
        oListAllPrefixWord.add(new PrefixWord(VISITOBJECTWORD, UpdateOngoingVisitCommand.COMMAND_WORD, "ud/"));
        oListAllPrefixWord.add(new PrefixWord(VISITOBJECTWORD, UpdateOngoingVisitCommand.COMMAND_WORD, "r/"));

        oListAllPrefixWord.add(new PrefixWord(PATIENTOBJECTWORD, FindCommand.COMMAND_WORD, "t/"));
        oListAllPrefixWord.add(new PrefixWord(APPOINTMENTBJECTWORD, EditCommand.COMMAND_WORD, "c/"));

        return oListAllPrefixWord;
    }

    /**
     * Initialise object list
     */
    private ObservableList<AutoCompleteWord> initAllObjectWordList() {
        ObservableList<AutoCompleteWord> oListAllObjectWord = FXCollections.observableArrayList();
        oListAllObjectWord.add(new ObjectWord(PATIENTOBJECTWORD));
        oListAllObjectWord.add(new ObjectWord(APPLICATIONOBJECTWORD));
        oListAllObjectWord.add(new ObjectWord(VISITOBJECTWORD));
        oListAllObjectWord.add(new ObjectWord(APPOINTMENTBJECTWORD));

        return oListAllObjectWord;
    }

    public ObservableList<AutoCompleteWord> getOListAllCommandWord() {
        return FXCollections.observableArrayList(oListAllCommandWord);
    }

    public ObservableList<AutoCompleteWord> getOListAllPrefixWord() {
        return FXCollections.observableArrayList(oListAllPrefixWord);
    }

    public ObservableList<AutoCompleteWord> getOListAllObjectWord() {
        return FXCollections.observableArrayList(oListAllObjectWord);
    }

    /**
     * Dummy method to produce index word list
     * (total patient/medcon etc may change during runtime (to improve later))
     *
     * @return list of index word
     */
    public ObservableList<AutoCompleteWord> generateOListAllIndexWord() {
        ObservableList<AutoCompleteWord> oListAllIndexWord = FXCollections.observableArrayList();
        oListAllIndexWord.add(new IndexWord("1"));
        oListAllIndexWord.add(new IndexWord("2"));
        oListAllIndexWord.add(new IndexWord("3"));
        return oListAllIndexWord;
    }
}

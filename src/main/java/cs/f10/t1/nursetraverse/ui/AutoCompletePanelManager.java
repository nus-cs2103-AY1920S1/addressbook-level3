package cs.f10.t1.nursetraverse.ui;

import java.util.LinkedList;

import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteListHandler;
import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteWordStorage;
import cs.f10.t1.nursetraverse.autocomplete.MatchedWordUpdater;
import cs.f10.t1.nursetraverse.autocomplete.UserinputParserUtil;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.input.KeyCode;

/**
 * A manager class of AutoCompletePanel UI
 * This class was created to process and handle requests to update autocomplete panel
 */
public class AutoCompletePanelManager implements UiObserver, DataSender {
    private AutoCompleteListHandler autoCompleteListHandler;
    private MatchedWordUpdater matchedWordUpdater;
    private AutoCompletePanel autoCompletePanel;

    private int selectedIndex = 0;

    public AutoCompletePanelManager(AutoCompletePanel autoCompletePanel,
                                    FilteredList<Patient> patList,
                                    FilteredList<Appointment> apptList,
                                    ObservableList<HistoryRecord> historyList) {
        AutoCompleteWordStorage autoCompleteWordStorage = new AutoCompleteWordStorage(patList, apptList, historyList);
        this.autoCompletePanel = autoCompletePanel;
        autoCompleteListHandler = new AutoCompleteListHandler(autoCompleteWordStorage);
        matchedWordUpdater = new MatchedWordUpdater(autoCompleteWordStorage, autoCompleteListHandler);

        // Initialise list in autocomplete panel
        updateListView("");
    }

    public AutoCompleteWord getSelected() {
        return autoCompletePanel.getSelected();
    }

    public void setSelected(int index) {
        if (index > getTotalItems() - 1) {
            index = getTotalItems() - 1;
        } else if (index < 0) {
            index = 0;
        }
        this.selectedIndex = index;
        autoCompletePanel.getAutoCompleteWordListView().getSelectionModel().select(selectedIndex);
        autoCompletePanel.getAutoCompleteWordListView().scrollTo(selectedIndex);
    }

    public int getTotalItems() {
        return autoCompletePanel.getAutoCompleteWordListView().getItems().size();
    }

    /**
     * Change current list if needed and update suggested words in current list
     *
     * @param currentPhraseInCommandBox current String in command
     */
    public void updateListView(String currentPhraseInCommandBox) {
        // Pre-parse userinput before passing to list handling methods
        LinkedList<String> parsedList = UserinputParserUtil.parse(currentPhraseInCommandBox);

        // Check and update matched words
        LinkedList<AutoCompleteWord> matchedWords = matchedWordUpdater.findMatchedWords(parsedList);

        // Generate list using autoCompleteListHandler and and set list in autoCompletePanel
        autoCompletePanel.setList(autoCompleteListHandler.generateList(matchedWords, parsedList));
    }

    @Override
    public void update(KeyCode keyCode) {
        // handle up and down key separately
        if (keyCode == KeyCode.UP) {
            setSelected(selectedIndex - 1);
        } else if (keyCode == KeyCode.DOWN) {
            setSelected(selectedIndex + 1);
        }
    }

    @Override
    public void update(KeyCode keyCode, String resultString) {
        // Do not handle up down button pressed
        if (keyCode != KeyCode.UP && keyCode != KeyCode.DOWN) {
            updateListView(resultString);
        }
    }

    @Override
    public String[] sendData() {
        String textAfterSelection = matchedWordUpdater.getCombinedMatchedWords() + getSelected().getSuggestedWord();
        String selectedWordDescription = getSelected().getDescription();

        return new String[]{textAfterSelection, selectedWordDescription};
    }

}

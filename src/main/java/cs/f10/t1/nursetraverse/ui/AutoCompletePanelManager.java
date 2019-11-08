package cs.f10.t1.nursetraverse.ui;

import java.util.LinkedList;

import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteListHandler;
import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteWord;
import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteWordStorage;
import cs.f10.t1.nursetraverse.autocomplete.MatchedWordUpdater;
import cs.f10.t1.nursetraverse.autocomplete.UserinputParserUtil;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.input.KeyCode;

/**
 * A manager class of AutoCompletePanel UI
 * This class was created to handle requests to update autocomplete panel
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
        return autoCompletePanel.getAutoCompleteWordListView().getSelectionModel().getSelectedItem();
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
        String[] segments = UserinputParserUtil.splitIntoSegments(currentPhraseInCommandBox);
        LinkedList<String> firstSegmentParts = UserinputParserUtil.parseFirstSegment(segments[0]);
        // Check and update matched words
        matchedWordUpdater.updateMatchedWords(segments, firstSegmentParts);
        // Choose initial list
        ObservableList<AutoCompleteWord> chosenList = autoCompleteListHandler
                .chooseList(matchedWordUpdater.getMatchedAutoCompleteWords());
        // Filter list based on previous matched words
        ObservableList<AutoCompleteWord> filteredList = autoCompleteListHandler
                .filterList(matchedWordUpdater.getMatchedAutoCompleteWords(), chosenList);
        // Update list based on userinput
        ObservableList<AutoCompleteWord> updatedList = autoCompleteListHandler
                .updateList(matchedWordUpdater.getMatchedAutoCompleteWords(),
                        filteredList, segments, firstSegmentParts);

        autoCompletePanel.updateListView(updatedList);
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

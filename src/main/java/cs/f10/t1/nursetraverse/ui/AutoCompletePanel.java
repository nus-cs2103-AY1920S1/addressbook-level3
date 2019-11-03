package cs.f10.t1.nursetraverse.ui;

import java.util.LinkedList;

import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteListHandler;
import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteWord;
import cs.f10.t1.nursetraverse.autocomplete.AutoCompleteWordStorage;
import cs.f10.t1.nursetraverse.autocomplete.MatchedWordUpdater;
import cs.f10.t1.nursetraverse.autocomplete.UserinputParserUtil;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of suggested words cards.
 */
public class AutoCompletePanel extends UiPart<Region> implements Observer, DataSender {
    private static final String FXML = "AutoCompletePanel.fxml";

    private AutoCompleteListHandler autoCompleteListHandler;
    private MatchedWordUpdater matchedWordUpdater;

    private int selectedIndex = 0;

    @FXML
    private ListView<AutoCompleteWord> autoCompleteWordListView;

    public AutoCompletePanel(FilteredList<Patient> patList, FilteredList<Appointment> apptList) {
        super(FXML);

        AutoCompleteWordStorage autoCompleteWordStorage = new AutoCompleteWordStorage(patList, apptList);
        autoCompleteListHandler = new AutoCompleteListHandler(autoCompleteWordStorage);
        matchedWordUpdater = new MatchedWordUpdater(autoCompleteWordStorage, autoCompleteListHandler);

        // Initialise list
        updateListView("");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AutoCompleteWord} using a {@code AutoCompleteCard}
     */
    class AutoCompleteListViewCell extends ListCell<AutoCompleteWord> {
        @Override
        protected void updateItem(AutoCompleteWord autoCompleteWord, boolean empty) {
            super.updateItem(autoCompleteWord, empty);
            if (empty || autoCompleteWord == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AutoCompleteCard(autoCompleteWord.getSuggestedWord()).getRoot());
            }
        }
    }

    public AutoCompleteWord getSelected() {
        return autoCompleteWordListView.getSelectionModel().getSelectedItem();
    }

    public void setSelected(int index) {
        if (index > getTotalItems() - 1) {
            index = getTotalItems() - 1;
        } else if (index < 0) {
            index = 0;
        }
        this.selectedIndex = index;
        autoCompleteWordListView.getSelectionModel().select(selectedIndex);
        autoCompleteWordListView.scrollTo(selectedIndex);
    }

    public int getTotalItems() {
        return autoCompleteWordListView.getItems().size();
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

        autoCompleteWordListView.setItems(updatedList);
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
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

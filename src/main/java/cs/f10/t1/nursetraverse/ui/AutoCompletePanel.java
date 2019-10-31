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
import javafx.scene.layout.Region;

/**
 * Panel containing the list of suggested words cards.
 */
public class AutoCompletePanel extends UiPart<Region> {
    private static final String FXML = "AutoCompletePanel.fxml";

    private int selectedIndex = 0;

    private AutoCompleteListHandler autoCompleteListHandler;
    private MatchedWordUpdater matchedWordUpdater;

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

    public int getSelectedIndex() {
        return selectedIndex;
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
                .chooseInitialList(matchedWordUpdater.getMatchedAutoCompleteWords());
        // Filter list based on previous matched words
        ObservableList<AutoCompleteWord> filteredList = autoCompleteListHandler
                .filterList(matchedWordUpdater.getMatchedAutoCompleteWords(), chosenList);
        // Update list based on userinput
        ObservableList<AutoCompleteWord> updatedList = autoCompleteListHandler
                .updateList(matchedWordUpdater.getMatchedAutoCompleteWords(),
                        filteredList, segments, firstSegmentParts);

        // Add '-' to each object word displayed for user understanding
        autoCompleteListHandler.addDashToObjectWordList(updatedList);
        autoCompleteWordListView.setItems(updatedList);
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
    }

    /**
     * @return string representation of all the matched words
     */
    public String getStringAfterSelection() {
        StringBuilder combinedMatchedWords = new StringBuilder();
        for (AutoCompleteWord autoCompleteWord : matchedWordUpdater.getMatchedAutoCompleteWords()) {
            combinedMatchedWords
                    .append(autoCompleteWord.getSuggestedWord())
                    .append(autoCompleteWord.getConnectorChar());
        }
        combinedMatchedWords.append(getSelected().getSuggestedWord());
        return combinedMatchedWords.toString();
    }
}

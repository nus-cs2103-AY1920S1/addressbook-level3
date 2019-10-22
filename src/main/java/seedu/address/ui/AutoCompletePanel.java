package seedu.address.ui;

import java.util.LinkedList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.autocomplete.AutoCompleteListFilter;
import seedu.address.autocomplete.AutoCompleteListUpdater;
import seedu.address.autocomplete.AutoCompleteWord;
import seedu.address.autocomplete.AutoCompleteWordStorage;
import seedu.address.autocomplete.CommandWord;
import seedu.address.autocomplete.UserinputParserUtil;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of suggested words cards.
 */
public class AutoCompletePanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(AutoCompletePanel.class);
    private static final String FXML = "AutoCompletePanel.fxml";

    private int selectedIndex = 0;

    private LinkedList<AutoCompleteWord> matchedAutoCompleteWords = new LinkedList<>();

    private AutoCompleteListUpdater autoCompleteListUpdater;
    private AutoCompleteWordStorage autoCompleteWordStorage;
    private AutoCompleteListFilter autoCompleteListFilter;

    @FXML
    private ListView<AutoCompleteWord> autoCompleteWordListView;

    public AutoCompletePanel() {
        super(FXML);

        autoCompleteWordStorage = new AutoCompleteWordStorage();
        autoCompleteListUpdater = new AutoCompleteListUpdater();
        autoCompleteListFilter = new AutoCompleteListFilter();
        autoCompleteListUpdater.setList(autoCompleteWordStorage.getOListAllObjectWord());
        autoCompleteWordListView.setItems(autoCompleteListUpdater.getOListSuggestedWords());
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
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
                setGraphic(new AutoCompleteCard(autoCompleteWord).getRoot());
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
        autoCompleteWordListView.getSelectionModel().select(index);
        this.selectedIndex = index;
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
        updateMatchedWords(currentPhraseInCommandBox);
        resetList();
        autoCompleteListUpdater.updateSuggestedWordsInList(matchedAutoCompleteWords.size(), currentPhraseInCommandBox);
    }

    /**
     * Re-set and filter list to be suggested
     */
    public void resetList() {
        if (matchedAutoCompleteWords.size() == 0) {
            // Set to object list
            autoCompleteListUpdater.setList(autoCompleteWordStorage.getOListAllObjectWord());
        } else if (matchedAutoCompleteWords.size() == 1) {
            // Set to command list
            ObservableList<AutoCompleteWord> filteredList = autoCompleteListFilter
                    .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllCommandWord());
            autoCompleteListUpdater.setList(filteredList);
        } else if (matchedAutoCompleteWords.size() == 2) {
            // Set to prefix/index/empty list
            if (((CommandWord) matchedAutoCompleteWords.get(1)).hasIndex()) {
                autoCompleteListUpdater.setList(autoCompleteWordStorage.generateOListAllIndexWord());
            } else if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
                ObservableList<AutoCompleteWord> filteredList = autoCompleteListFilter
                        .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord());
                autoCompleteListUpdater.setList(filteredList);
            } else {
                autoCompleteListUpdater.setList(FXCollections.observableArrayList());
            }
        } else {
            // Set to prefix/empty list
            if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
                ObservableList<AutoCompleteWord> filteredList = autoCompleteListFilter
                        .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord());
                autoCompleteListUpdater.setList(filteredList);
            } else {
                autoCompleteListUpdater.setList(FXCollections.observableArrayList());
            }
        }
        autoCompleteWordListView.setItems(autoCompleteListUpdater.getOListSuggestedWords());
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
    }

    /**
     * Update matchedAutoCompleteWords list given userinput
     *
     * @param currentPhraseInCommandBox string in command box text field
     */
    public void updateMatchedWords(String currentPhraseInCommandBox) {
        // segments[0] -> object-command
        // segments[1] -> either prefix or index
        // segments[2] onwards -> prefix
        String[] segments = UserinputParserUtil.splitIntoSegments(currentPhraseInCommandBox);
        matchedAutoCompleteWords.clear();

        boolean isCorrectFirstSegment = matchFirstSegment(segments[0]);

        if (segments.length >= 2) {
            boolean isCorrectSecondSegment = false;
            if (isCorrectFirstSegment) {
                isCorrectSecondSegment = matchSecondSegment(segments[1]);
            }
            if (segments.length >= 3) {
                if (isCorrectFirstSegment && isCorrectSecondSegment) {
                    matchRestOfSegment(segments);
                }
            }
        }
    }

    /**
     * Check if first segment of userinput matches with current matched words
     *
     * @param firstSegment first segment of userinput
     * @return true if first segment matches with current matched words
     */
    public boolean matchFirstSegment(String firstSegment) {
        boolean isCorrectObjectWord = false;
        boolean isCorrectCommandWord = false;

        LinkedList<String> firstSegmentParts = UserinputParserUtil.parseFirstSegment(firstSegment);
        if (firstSegmentParts.size() >= 1) {
            // First object word
            for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage.getOListAllObjectWord()) {
                if (firstSegmentParts.get(0).matches(autoCompleteWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(autoCompleteWord);
                    isCorrectObjectWord = true;
                }
            }
            // Second command word
            if (firstSegmentParts.size() == 2 && isCorrectObjectWord) {
                for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage.getOListAllCommandWord()) {
                    if (firstSegmentParts.get(1).matches(autoCompleteWord.getSuggestedWord())) {
                        matchedAutoCompleteWords.add(autoCompleteWord);
                        isCorrectCommandWord = true;
                    }
                }
            }
        }
        return isCorrectObjectWord && isCorrectCommandWord;
    }

    /**
     * Check if second segment of userinput matches with current matched words
     *
     * @param secondSegment second segment of userinput
     * @return true if second segment matches with current matched words
     */
    public boolean matchSecondSegment(String secondSegment) {
        boolean isCorrect = false;

        if (((CommandWord) matchedAutoCompleteWords.get(1)).hasIndex()) {
            for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage.generateOListAllIndexWord()) {
                if (secondSegment.equals(autoCompleteWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(autoCompleteWord);
                    isCorrect = true;
                }
            }
        } else if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
            for (AutoCompleteWord autoCompleteWord : autoCompleteListFilter
                    .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord())) {
                if (secondSegment.equals(autoCompleteWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(autoCompleteWord);
                    isCorrect = true;
                }
            }
        }
        return isCorrect;
    }

    /**
     * Check if all other segment of userinput matches with current matched words
     *
     * @param segments Array of segments to be checked
     */
    public void matchRestOfSegment(String[] segments) {
        for (int i = 3; i <= segments.length; i++) {
            if (matchedAutoCompleteWords.size() == i) {
                for (AutoCompleteWord autoCompleteWord : autoCompleteListFilter
                        .filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord())) {
                    if (segments[i - 1].equals(autoCompleteWord.getSuggestedWord())) {
                        matchedAutoCompleteWords.add(autoCompleteWord);
                    }
                }
            }
        }
    }

    public LinkedList<AutoCompleteWord> getMatchedWordsList() {
        return matchedAutoCompleteWords;
    }
}

package seedu.address.ui;

import java.util.LinkedList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.autocomplete.*;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of suggested words.
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

    public void resetList() {
        if (matchedAutoCompleteWords.size() == 0) {
            // Set to object list
            autoCompleteListUpdater.setList(autoCompleteWordStorage.getOListAllObjectWord());
        } else if (matchedAutoCompleteWords.size() == 1) {
            // Set to command list
            ObservableList<AutoCompleteWord> filteredList = autoCompleteListFilter.filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllCommandWord());
            autoCompleteListUpdater.setList(filteredList);
        } else if (matchedAutoCompleteWords.size() == 2) {
            // Set to prefix/index/empty list
            if (((CommandWord) matchedAutoCompleteWords.get(1)).hasIndex()) {
                autoCompleteListUpdater.setList(autoCompleteWordStorage.generateOListAllIndexWord());
            } else if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
                ObservableList<AutoCompleteWord> filteredList = autoCompleteListFilter.filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord());
                autoCompleteListUpdater.setList(filteredList);
            } else {
                autoCompleteListUpdater.setList(FXCollections.observableArrayList());
            }
        } else {
            // Set to prefix/empty list
            if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
                ObservableList<AutoCompleteWord> filteredList = autoCompleteListFilter.filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord());
                autoCompleteListUpdater.setList(filteredList);
            } else {
                autoCompleteListUpdater.setList(FXCollections.observableArrayList());
            }
        }
        autoCompleteWordListView.setItems(autoCompleteListUpdater.getOListSuggestedWords());
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
    }

    // segments[0] -> object-command
    // segments[1] -> either prefix or index
    // segments[2] onwards -> prefix
    public void updateMatchedWords(String currentPhraseInCommandBox) {
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

    public boolean matchFirstSegment(String firstSegment) {
        boolean isCorrectObjectWord = false;
        boolean isCorrectCommandWord = false;

        LinkedList<String> firstSegmentParts = UserinputParserUtil.parseFirstSegment(firstSegment);
        if (firstSegmentParts.size() >= 1) {
            // First object word
            for (AutoCompleteWord currentACWord : autoCompleteWordStorage.getOListAllObjectWord()) {
                if (firstSegmentParts.get(0).matches(currentACWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(currentACWord);
                    isCorrectObjectWord = true;
                }
            }
            // Second command word
            if (firstSegmentParts.size() == 2 && isCorrectObjectWord) {
                for (AutoCompleteWord currentACWord : autoCompleteWordStorage.getOListAllCommandWord()) {
                    if (firstSegmentParts.get(1).matches(currentACWord.getSuggestedWord())) {
                        matchedAutoCompleteWords.add(currentACWord);
                        isCorrectCommandWord = true;
                    }
                }
            }
        }
        return isCorrectObjectWord && isCorrectCommandWord;
    }

    public boolean matchSecondSegment(String secondSegment) {
        boolean isCorrect = false;

        if (((CommandWord) matchedAutoCompleteWords.get(1)).hasIndex()) {
            for (AutoCompleteWord currentACWord : autoCompleteWordStorage.generateOListAllIndexWord()) {
                if (secondSegment.equals(currentACWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(currentACWord);
                    isCorrect = true;
                }
            }
        } else if (((CommandWord) matchedAutoCompleteWords.get(1)).hasPrefix()) {
            for (AutoCompleteWord currentACWord : autoCompleteListFilter.filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord())) {
                if (secondSegment.equals(currentACWord.getSuggestedWord())) {
                    matchedAutoCompleteWords.add(currentACWord);
                    isCorrect = true;
                }
            }
        }
        return isCorrect;
    }

    public void matchRestOfSegment(String[] segments) {
        for (int i = 3; i <= segments.length; i++) {
            if (matchedAutoCompleteWords.size() == i) {
                for (AutoCompleteWord currentACWord : autoCompleteListFilter.filterList(matchedAutoCompleteWords, autoCompleteWordStorage.getOListAllPrefixWord())) {
                    if (segments[i - 1].equals(currentACWord.getSuggestedWord())) {
                        matchedAutoCompleteWords.add(currentACWord);
                    }
                }
            }
        }
    }

    public LinkedList<AutoCompleteWord> getMatchedWordsList() {
        return matchedAutoCompleteWords;
    }
}

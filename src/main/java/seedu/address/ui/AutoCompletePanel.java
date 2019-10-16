package seedu.address.ui;

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

    private AutoCompleteWordHandler autoCompleteWordHandler;
    private AutoCompleteWordStorage autoCompleteWordStorage;

    @FXML
    private ListView<AutoCompleteWord> autoCompleteWordListView;

    public AutoCompletePanel() {
        super(FXML);
        // Set initial list
        autoCompleteWordStorage = new AutoCompleteWordStorage();
        autoCompleteWordHandler = new AutoCompleteWordHandler(autoCompleteWordStorage.getOListAllObjectWord());
        autoCompleteWordListView.setItems(autoCompleteWordHandler.getOListSuggestedWords());
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
        logger.info("Updated list");

        // Choose which list to set
        boolean isNextList = chooseList(currentPhraseInCommandBox);

        // Update words in current list base on string in command box
        if (isNextList) {
            System.out.println("updated after list changed");
            autoCompleteWordHandler.updateSuggestedWordsInList("");
        } else {
            System.out.println("updated without list change");
            autoCompleteWordHandler.updateSuggestedWordsInList(currentPhraseInCommandBox);
        }
    }

    public boolean chooseList(String currentPhraseInCommandBox) {
        // Whenever commandTextField is blank, set to object list
        if (currentPhraseInCommandBox.isBlank()) {
            logger.info("Changed to object list");
            // change to object list
            setCurrentList(autoCompleteWordStorage.getOListAllObjectWord());
        } else {
            String[] userinputs = currentPhraseInCommandBox.split(" ");
            if (userinputs.length == 1) {
                // change to command list
                for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage.getOListAllObjectWord()) {
                    // if the last word matches any words in current list
                    if (autoCompleteWord.getSuggestedWord().matches(userinputs[0])) {
                        logger.info("Changed to command list");
                        setCurrentList(autoCompleteWordStorage.getOListAllCommandWord());
                        return true;
                    }
                }
                // Go back to previous list
                // At this point there is only one word in commandbox textfield and the word does not match any of the object word list
                setCurrentList(autoCompleteWordStorage.getOListAllObjectWord());
            } else if (userinputs.length == 2) {
                // change to command list
                for (AutoCompleteWord autoCompleteWord : autoCompleteWordStorage.getOListAllCommandWord()) {
                    // if the last word matches any words in current list
                    if (autoCompleteWord.getSuggestedWord().matches(userinputs[1])) {
                        logger.info("Changed to prefix list");
                        setCurrentList(autoCompleteWordStorage.getOListAllPrefixWord());
                        return true;
                    }
                }
                // Go back to previous list
                // At this point there is two word in commandbox textfield but the 2nd word does not match any of the command word list
                setCurrentList(autoCompleteWordStorage.getOListAllCommandWord());
            }
        }
        return false;
    }

    public void setCurrentList(ObservableList<AutoCompleteWord> list) {
        autoCompleteWordHandler.setList(list);
        autoCompleteWordListView.setItems(autoCompleteWordHandler.getOListSuggestedWords());
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
    }
}

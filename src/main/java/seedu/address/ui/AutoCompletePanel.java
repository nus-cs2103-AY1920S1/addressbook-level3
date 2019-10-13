package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.autocomplete.AutoCompleteWord;
import seedu.address.autocomplete.AutoCompleteWordHandler;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class AutoCompletePanel extends UiPart<Region> {
    private AutoCompleteWordHandler autoCompleteWordHandler;

    private static final String FXML = "AutoCompletePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AutoCompletePanel.class);

    @FXML
    private ListView<AutoCompleteWord> autoCompleteWordListView;

    public AutoCompletePanel(AutoCompleteWordHandler autoCompleteWordHandler) {
        super(FXML);
        this.autoCompleteWordHandler = autoCompleteWordHandler;
        autoCompleteWordListView.setItems(autoCompleteWordHandler.getOListOfSuggestionWords());
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class AutoCompleteListViewCell extends ListCell<AutoCompleteWord> {
        @Override
        protected void updateItem(AutoCompleteWord autoCompleteWord, boolean empty) {
            super.updateItem(autoCompleteWord, empty);
            System.out.println("updateItem called");

            if (empty || autoCompleteWord == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AutoCompleteCard(autoCompleteWord).getRoot());
            }
        }
    }

    /*public void setList() {

    }*/

    public AutoCompleteWord getSelected() {
        return autoCompleteWordListView.getSelectionModel().getSelectedItem();
    }

    public void select(int index) {
        autoCompleteWordListView.getSelectionModel().select(index);
    }

    public int getTotalItems() {
        return autoCompleteWordListView.getItems().size();
    }

    public void updateListView(String currentPhraseInCommandBox) {
        autoCompleteWordHandler.updateList(currentPhraseInCommandBox);
    }
}

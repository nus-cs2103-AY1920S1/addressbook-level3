package cs.f10.t1.nursetraverse.ui;

import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * UI Panel containing the list of suggested words cards.
 */
public class AutoCompletePanel extends UiPart<Region> {
    private static final String FXML = "AutoCompletePanel.fxml";

    @FXML
    private ListView<AutoCompleteWord> autoCompleteWordListView;

    public AutoCompletePanel() {
        super(FXML);
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

    /**
     * Change suggested list in autocomplete panel
     *
     * @param updatedList new list to be set in autocomplete panel
     */
    public void setList(ObservableList<AutoCompleteWord> updatedList) {
        autoCompleteWordListView.setItems(updatedList);
        autoCompleteWordListView.setCellFactory(listView -> new AutoCompleteListViewCell());
    }

    public AutoCompleteWord getSelected() {
        return autoCompleteWordListView.getSelectionModel().getSelectedItem();
    }

    public ListView<AutoCompleteWord> getAutoCompleteWordListView() {
        return autoCompleteWordListView;
    }
}

package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Expense;

/**
 * Panel containing the list of entries.
 */
public class EntryListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EntryListPanel.class);

    @FXML
    private ListView<Expense> entryListView;

    public EntryListPanel(ObservableList<Expense> entryList) {
        super(FXML);
        entryListView.setItems(entryList);
        entryListView.setCellFactory(listView -> new EntryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class EntryListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntryCard(entry, getIndex() + 1).getRoot());
            }
        }
    }

}

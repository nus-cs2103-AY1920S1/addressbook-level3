package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Entry;

/**
 * Side panel for budgets.
 */
public class ReminderPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";

    @FXML
    private ListView<Entry> reminderListView;

    public ReminderPanel(ObservableList<Entry> remindersList) {
        super(FXML);
        reminderListView.setItems(remindersList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ReminderListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
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


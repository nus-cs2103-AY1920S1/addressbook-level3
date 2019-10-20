package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of commands.
 */
public class HistoryListPanel extends UiPart<Region> {
    private static final String FXML = "HistoryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HistoryListPanel.class);

    @FXML
    private ListView<Pair<String, String>> historyListView;

    public HistoryListPanel(ObservableList<Pair<String, String>> historyList) {
        super(FXML);
        historyListView.setItems(historyList);
        historyListView.setCellFactory(listView -> new HistoryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Pair<String, String>} using a {@code HistoryCard}.
     */
    class HistoryListViewCell extends ListCell<Pair<String, String>> {
        @Override
        protected void updateItem(Pair<String, String> command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HistoryCard(command).getRoot());
            }
        }
    }

}

package seedu.guilttrip.ui.history;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.ui.UiPart;

/**
 * Side panel for budgets.
 */
public class HistoryPanel extends UiPart<Region> {
    private static final String FXML = "/history/HistoryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HistoryPanel.class);

    @FXML
    private ListView<String> historyListView;

    public HistoryPanel(ObservableList<String> historyList) {
        super(FXML);
        historyListView.setItems(historyList);
        historyListView.setCellFactory(listView -> new HistoryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class HistoryListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String history, boolean empty) {
            super.updateItem(history, empty);

            if (empty || history == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HistoryCard(history, getIndex() + 1).getRoot());
            }
        }
    }

}

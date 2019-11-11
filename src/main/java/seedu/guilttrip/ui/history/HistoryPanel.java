package seedu.guilttrip.ui.history;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.ui.UiPart;

import java.util.ArrayList;
import java.util.logging.Logger;

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
        //historyListView.setCellFactory(listView -> new HistoryListViewCell());
    }

    /*
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.

    class BudgetListViewCell extends ListCell<Budget> {
        @Override
        protected void updateItem(Budget entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BudgetCard(entry, getIndex() + 1).getRoot());
            }
        } */

}

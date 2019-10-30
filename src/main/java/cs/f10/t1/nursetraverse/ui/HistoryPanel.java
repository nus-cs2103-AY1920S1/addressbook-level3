package cs.f10.t1.nursetraverse.ui;

import cs.f10.t1.nursetraverse.model.HistoryRecord;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel showing the application command history list.
 */
public class HistoryPanel extends UiPart<Region> {
    private static final String FXML = "HistoryPanel.fxml";

    @FXML
    private ListView<HistoryRecord> historyView;

    public HistoryPanel(ObservableList<HistoryRecord> historyRecordList) {
        super(FXML);
        historyView.setItems(historyRecordList);
        historyView.setCellFactory(listView -> new HistoryRecordViewCell());
        historyView.setPlaceholder(new Label("No records in the history."));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code HistoryRecord} using a {@code HistoryRecordCard}.
     */
    class HistoryRecordViewCell extends ListCell<HistoryRecord> {

        @Override
        protected void updateItem(HistoryRecord record, boolean empty) {
            super.updateItem(record, empty);

            if (empty || record == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HistoryRecordCard(record).getRoot());
            }
        }
    }
}

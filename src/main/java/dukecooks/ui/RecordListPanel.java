package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.health.components.Record;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of records.
 */
public class RecordListPanel extends UiPart<Region> {
    private static final String FXML = "RecordListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecordListPanel.class);

    @FXML
    private Label title;

    @FXML
    private ListView<Record> sidePanel;

    @FXML
    private ListView<Record> recordListView;

    public RecordListPanel(ObservableList<Record> recordList) {
        super(FXML);
        recordListView.setItems(recordList);
        recordListView.setCellFactory(listView -> new RecordListViewCell());

        sidePanel.setItems(recordList);
        sidePanel.setCellFactory(listView -> new RecordListViewCell());
    }

    /**
     * Hide all inner components within Health Record Panel.
     */
    private void hidePanels() {
        sidePanel.setVisible(false);
        recordListView.setVisible(false);
    }

    /**
     * Display inner components within Health Record Panel.
     */
    private void showPanels(boolean isShowSidePanel, boolean isShowListView) {
        sidePanel.setVisible(isShowSidePanel);
        recordListView.setVisible(isShowListView);
    }

    /**
     * Switch view within Health Record Panel.
     */
    @FXML
    void handleSwitch(String type) {
        switch (type) {
        case "all":
            showPanels(false, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid Health Record page type!");
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Record} using a {@code RecordCard}.
     */
    class RecordListViewCell extends ListCell<Record> {
        @Override
        protected void updateItem(Record record, boolean empty) {
            super.updateItem(record, empty);

            if (empty || record == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecordCard(record, getIndex() + 1).getRoot());
            }
        }
    }

}

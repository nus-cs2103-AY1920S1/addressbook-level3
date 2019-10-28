package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.health.components.Record;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
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
    private ListView<Record> sideView;

    @FXML
    private FlowPane flowPaneView;

    public RecordListPanel(ObservableList<Record> recordList) {
        super(FXML);
        initializeFlowPaneView(recordList);
        initializeSidePanel(recordList);
    }

    /**
     * Initialises FlowPane Config.
     * Gives the overview of health records recorded by user
     */
    void initializeFlowPaneView(ObservableList<Record> recordList) {
        flowPaneView.setHgap(5);
        flowPaneView.setVgap(5);
        flowPaneView.setPadding(new Insets(10, 10, 10, 10));

        // Creates a RecordCard for each Record and adds to FlowPane
        int i = 0;
        for (Record record : recordList) {
            flowPaneView.getChildren().add(new RecordCard(record, i).getRoot());
            i++;
        }
        //add listener for new record changes
        recordList.addListener((ListChangeListener<Record>) c -> {
            flowPaneView.getChildren().clear();
            int x = 0;
            for (Record r: recordList) {
                flowPaneView.getChildren().add(new RecordCard(r, x).getRoot());
                x++;
            }
        });
    }

    /**
     * Initialises SidePanel Config.
     * Shows the type of health records that can be recorded
     */
    //TODO: TO CONTINUE REFINING - INCOMPLETE!
    void initializeSidePanel(ObservableList<Record> recordList) {
        sideView.setItems(recordList);
        sideView.setCellFactory(listView -> new RecordListViewCell());
    }

    /**
     * Hide all inner components within Health Record Panel.
     */
    private void hidePanels() {
        sideView.setVisible(false);
        flowPaneView.setVisible(false);
    }

    /**
     * Display inner components within Health Record Panel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean isShowSideView, boolean isShowCardView) {
        sideView.setVisible(isShowSideView);
        sideView.setManaged(isShowSideView);
        flowPaneView.setVisible(isShowCardView);
        flowPaneView.setManaged(isShowCardView);
    }

    /**
     * Switch view within Health Record Panel.
     */
    @FXML
    void handleSwitch(String type) {
        switch (type) {
        case "all":
            showPanels(true, true);
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
                setGraphic(new RecordListView(record, getIndex() + 1).getRoot());
            }
        }
    }

}

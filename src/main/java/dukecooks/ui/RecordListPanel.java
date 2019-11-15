package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.logic.ui.CustomRecordList;
import dukecooks.model.health.components.Record;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing the list of records.
 */
public class RecordListPanel extends UiPart<Region> {
    private static final String FXML = "RecordListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecordListPanel.class);

    private RecordTypeListPanel recordTypeDisplay;

    @FXML
    private Label title;

    @FXML
    private ListView<Record> allRecordsView;

    @FXML
    private FlowPane flowPaneView;

    @FXML
    private VBox recordTypeView;

    @FXML
    private VBox healthTypePage;

    @FXML
    private ScrollPane overviewPage;

    @FXML
    private Label allRecordsLabel;

    public RecordListPanel(ObservableList<Record> recordList) {
        super(FXML);
        initializeFlowPaneView(recordList);
        initializeAllRecordsPanel(recordList);
        initializeReviewTypeView(recordList);
    }

    /**
     * Initialises FlowPane Config.
     * Gives the overview of health records recorded by user
     */
    void initializeFlowPaneView(ObservableList<Record> recordList) {
        flowPaneView.setHgap(30);
        flowPaneView.setVgap(25);
        flowPaneView.setPadding(new Insets(10, 10, 10, 10));

        var ref = new Object() {
            private ObservableList<Record> summaryList = CustomRecordList.filterSummary(recordList);
        };

        // Creates a RecordTypeCard for each Record and adds to FlowPane
        int i = 0;
        for (Record record : ref.summaryList) {
            flowPaneView.getChildren().add(new RecordTypeCard(record, i).getRoot());
            i++;
        }
        //add listener for new record changes
        recordList.addListener((ListChangeListener<Record>) c -> {
            flowPaneView.getChildren().clear();
            //update summary list
            ref.summaryList = CustomRecordList.filterSummary(recordList);
            int x = 0;
            for (Record r: ref.summaryList) {
                flowPaneView.getChildren().add(new RecordTypeCard(r, x).getRoot());
                x++;
            }
        });
    }

    /**
     * Initialises AllRecordsPanel Config.
     * Shows the type of health records that can be recorded
     */
    //TODO: TO CONTINUE REFINING - INCOMPLETE!
    void initializeAllRecordsPanel(ObservableList<Record> recordList) {
        allRecordsView.setItems(recordList);
        allRecordsView.setCellFactory(listView -> new RecordListViewCell());
    }

    void initializeReviewTypeView(ObservableList<Record> recordList) {
        recordTypeDisplay = new RecordTypeListPanel(recordList);
        recordTypeView.getChildren().add(recordTypeDisplay.getRoot());
    }

    /**
     * Hide all inner components within Health Record Panel.
     */
    private void hidePanels() {
        allRecordsView.setVisible(false);
        flowPaneView.setVisible(false);
        recordTypeView.setVisible(false);
    }

    /**
     * Display inner components within Health Record Panel.
     * Make use of boolean variables to decide which components to show/hide.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean isShowMainHeader, boolean isShowOverview,
                            boolean isShowHealthTypeView) {
        title.setVisible(isShowMainHeader);
        title.setManaged(isShowMainHeader);
        overviewPage.setVisible(isShowOverview);
        overviewPage.setManaged(isShowOverview);
        healthTypePage.setVisible(isShowHealthTypeView);
        healthTypePage.setManaged(isShowHealthTypeView);
    }

    /**
     * Switch view within Health Record Panel.
     */
    @FXML
    void handleSwitch(String type) {
        switch (type) {
        case "all":
            showPanels(true, true, false);
            break;

        case "type":
            showPanels(false, false, true);
            break;

        default:
            throw new AssertionError("Something's Wrong! Invalid Health Record page type!");
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Record} using a {@code RecordTypeCard}.
     */
    class RecordListViewCell extends ListCell<Record> {
        @Override
        protected void updateItem(Record record, boolean empty) {
            super.updateItem(record, empty);

            if (empty || record == null) {
                setGraphic(null);
                setText(null);
                allRecordsLabel.setText(null);
            } else {
                setGraphic(new RecordListCard(record, getIndex() + 1).getRoot());
                allRecordsLabel.setText("All Records");
            }
        }
    }

}

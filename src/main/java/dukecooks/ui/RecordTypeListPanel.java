package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.logic.parser.health.TimestampComparator;
import dukecooks.logic.ui.CustomRecordList;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.util.TypeUtil;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;


/**
 * Panel containing the list of records.
 */
public class RecordTypeListPanel extends UiPart<Region> {

    private static final String FXML = "RecordTypeListPanel.fxml";

    private String recordType;
    private String recordUnit;
    private final Logger logger = LogsCenter.getLogger(RecordTypeListPanel.class);

    @FXML
    private Label title;

    @FXML
    private ListView<Record> sideView;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xBarAxis;

    @FXML
    private NumberAxis yBarAxis;

    @FXML
    private LineChart<String, Integer> lineGraph;

    @FXML
    private StackedBarChart<String, Integer> barGraph;

    public RecordTypeListPanel(ObservableList<Record> recordList) {
        super(FXML);
        recordType = TypeUtil.TYPE_CALORIES;
        recordUnit = TypeUtil.UNIT_CALORIES;

        sideView.setItems(recordList.sorted(new TimestampComparator()));
        sideView.setCellFactory(listView -> new RecordListViewCell());

        initializeLineGraph(recordList);
        initializeBarGraph(recordList);

        chooseGraph(recordType);
        title.setText(recordType);
    }

    /**
     * Shows one of the graph and hides the other.
     */
    void showGraph(boolean isShowLineGraph, boolean isShowBarGraph) {
        lineGraph.setVisible(isShowLineGraph);
        lineGraph.setManaged(isShowLineGraph);
        barGraph.setVisible(isShowBarGraph);
        barGraph.setManaged(isShowBarGraph);
    }

    /**
     * Decides which graph is more suitable based on the health type that user is viewing.
     */
    void chooseGraph(String recordType) {
        String type = TypeUtil.TYPE_BEHAVIOUR.get(recordType);

        switch (type) {
        case TypeUtil.BEHAVIOUR_LATEST:
            showGraph(true, false);
            break;
        case TypeUtil.BEHAVIOUR_SUM:
            showGraph(false, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid Record Type to show Graph!");
        }
    }

    String getRecordType(ObservableList<Record> recordList) {
        return recordList.isEmpty() ? TypeUtil.TYPE_CALORIES
                : recordList.get(0).getType().type;
    }

    String getRecordUnit(ObservableList<Record> recordList) {
        return recordList.isEmpty() ? TypeUtil.UNIT_CALORIES
                : recordList.get(0).getType().unit;
    }

    /**
     * Updates the line graph in the component with orders over the past 30 days.
     */
    public void initializeLineGraph(ObservableList<Record> recordList) {
        xAxis.setLabel("Date");
        yAxis.setLabel("Value (" + recordUnit + ")");
        lineGraph.setAnimated(false);
        lineGraph.setLegendVisible(false);

        var ref = new Object() {
            private ObservableList<Record> records = CustomRecordList.filterRecordsByLatest(recordList);
        };

        lineGraph.getData().clear();
        setUpLineGraph(ref.records);

        //add listener for new record changes
        recordList.addListener((ListChangeListener<Record>) c -> {
            ref.records = CustomRecordList.filterRecordsByLatest(recordList);
            lineGraph.getData().clear();
            setUpLineGraph(ref.records);
            chooseGraph(getRecordType(recordList));
        });
    }

    public void setUpLineGraph(ObservableList<Record> record) {
        title.setText(getRecordType(record));
        yAxis.setLabel("Value (" + getRecordUnit(record) + ")");
        ObservableList<XYChart.Data<String, Integer>> data =
                FXCollections.<XYChart.Data<String, Integer>>observableArrayList();
        for (Record r: record) {
            data.add(new XYChart.Data<>(r.getTimestamp().getDate(), r.getValue().value));
        }
        XYChart.Series series = new XYChart.Series(data);
        lineGraph.getData().add(series);
    }

    /**
     * Updates the bar graph in the component with orders over the past 30 days.
     */
    public void initializeBarGraph(ObservableList<Record> recordList) {
        xBarAxis.setLabel("Date");
        yBarAxis.setLabel("Value (" + recordUnit + ")");
        barGraph.setAnimated(false);
        barGraph.setLegendVisible(false);

        var ref = new Object() {
            private ObservableList<Record> records = CustomRecordList.filterRecordsBySum(recordList);
        };

        barGraph.getData().clear();
        setUpBarGraph(ref.records);

        //add listener for new record changes
        recordList.addListener((ListChangeListener<Record>) c -> {
            ref.records = CustomRecordList.filterRecordsBySum(recordList);
            barGraph.getData().clear();
            setUpBarGraph(ref.records);
            chooseGraph(getRecordType(recordList));
        });
    }

    public void setUpBarGraph(ObservableList<Record> record) {
        title.setText(getRecordType(record));
        yBarAxis.setLabel("Value (" + getRecordUnit(record) + ")");
        ObservableList<XYChart.Data<String, Integer>> data =
                FXCollections.<XYChart.Data<String, Integer>>observableArrayList();
        for (Record r: record) {
            data.add(new XYChart.Data<>(r.getTimestamp().getDate(), r.getValue().value));
        }
        XYChart.Series series = new XYChart.Series(data);
        barGraph.getData().add(series);
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

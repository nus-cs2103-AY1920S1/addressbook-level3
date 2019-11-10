package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.logic.commands.health.ListHealthByTypeCommand;
import dukecooks.logic.ui.CustomRecordList;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;

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
        recordType = Type.Calories.toString();
        recordUnit = Type.Calories.getUnit();

        sideView.setItems(recordList);
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
        String type = Type.valueOf(recordType).getBehavior();

        switch (type) {
        case "latest":
            showGraph(true, false);
            break;
        case "sum":
            showGraph(false, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid Record Type to show Graph!");
        }
    }

    String getRecordType(ObservableList<Record> recordList) {
        return recordList.isEmpty() ? ListHealthByTypeCommand.getCurrentTypeView().toString()
                : recordList.get(0).getType().toString();
    }

    String getRecordUnit(ObservableList<Record> recordList) {
        return recordList.isEmpty() ? ListHealthByTypeCommand.getCurrentTypeView().getUnit()
                : recordList.get(0).getType().getUnit();
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
        xBarAxis.setLabel("Date");
        yAxis.setLabel("Value (" + getRecordUnit(record) + ")");
        ObservableList<XYChart.Data<String, Double>> data =
                FXCollections.<XYChart.Data<String, Double>>observableArrayList();
        for (Record r: record) {
            data.add(new XYChart.Data<>(r.getTimestamp().getDate().toString(), r.getValue().value));
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
        xBarAxis.setLabel("Date");
        yBarAxis.setLabel("Value (" + getRecordUnit(record) + ")");
        ObservableList<XYChart.Data<String, Double>> data =
                FXCollections.<XYChart.Data<String, Double>>observableArrayList();
        for (Record r: record) {
            data.add(new XYChart.Data<>(r.getTimestamp().getDate().toString(), r.getValue().value));
        }
        XYChart.Series series = new XYChart.Series(data);
        barGraph.getData().add(series);
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
            } else {
                setGraphic(new RecordTypeListCard(record, getIndex() + 1).getRoot());
            }
        }
    }

}

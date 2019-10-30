package dukecooks.ui;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import dukecooks.commons.core.LogsCenter;
import dukecooks.logic.parser.DateParser;
import dukecooks.logic.parser.health.TimestampComparator;
import dukecooks.model.health.components.Record;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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
    private LineChart<String, Integer> lineGraph;

    public RecordTypeListPanel(ObservableList<Record> recordList) {
        super(FXML);

        sideView.setItems(recordList.sorted(new TimestampComparator()));
        sideView.setCellFactory(listView -> new RecordListViewCell());

        initializeLineGraph(recordList);
    }

    /**
     * Filters records to show only the past 30 days records.
     * If there is more than 1 record made in a day, graph will show the latest record for that day.
     */
    ObservableList<Record> filterRecords(ObservableList<Record> recordList) {
        List<Record> result = recordList.stream()
                .filter(x -> DateParser.getCurrentDayDiff(x.getTimestamp().getDate()) < 31)
                .collect(Collectors.groupingBy(r -> r.getTimestamp().getDate(),
                                Collectors.maxBy(new TimestampComparator())))
                .values().stream()
                .map(Optional::get)
                .sorted(new TimestampComparator())
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }

    /**
     * Updates the line graph in the component with orders over the past 31 days. It does this by calling
     * the pollLastEntry method from a balanced binary search tree
     */
    public void initializeLineGraph(ObservableList<Record> recordList) {
        String unit = recordList.get(1).getType().unit;
        xAxis.setLabel("Date");
        yAxis.setLabel("Value (" + unit + ")");
        lineGraph.setAnimated(false);
        lineGraph.setLegendVisible(false);

        var ref = new Object() {
            private ObservableList<Record> records = filterRecords(recordList);
        };

        lineGraph.getData().clear();
        setUpLineGraph(ref.records);

        //add listener for new record changes
        recordList.addListener((ListChangeListener<Record>) c -> {
            ref.records = filterRecords(recordList);
            lineGraph.getData().clear();
            setUpLineGraph(ref.records);
            }
        );
    }

    public void setUpLineGraph(ObservableList<Record> record) {
        title.setText(record.get(0).getType().type);
        ObservableList<XYChart.Data<String, Integer>> data =
                FXCollections.<XYChart.Data<String, Integer>>observableArrayList();
        for (Record r: record) {
            data.add(new XYChart.Data<>(r.getTimestamp().getDate(), r.getValue().value));
        }
        XYChart.Series series = new XYChart.Series(data);
        lineGraph.getData().add(series);
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

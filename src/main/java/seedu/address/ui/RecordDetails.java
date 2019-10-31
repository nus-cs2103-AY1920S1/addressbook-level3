package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.model.performance.Record;

/**
 * Contains details of an athlete's record for a certain event.
 */
public class RecordDetails extends UiPart<Region> {

    private static final String FXML = "RecordDetails.fxml";
    private List<Record> records;
    private String name;

    @FXML
    private LineChart lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public RecordDetails(String name, List<Record> records) {
        super(FXML);
        this.name = name;
        this.records = records;
        populateChart();
    }

    /**
     * Populates the LineChart that is used to present athlete performance.
     */
    private void populateChart() {
        yAxis.setLowerBound(Record.getFastestTiming(records));
        yAxis.setUpperBound(Record.getSlowestTiming(records));
        XYChart.Series series = new XYChart.Series();
        for (Record record : records) {
            String date = record.getDate().toString();
            double timing = record.getTiming().getValue();
            series.getData().add(new XYChart.Data(date, timing));
        }
        series.setName(name + "'s Progress");
        lineChart.getData().addAll(series);
    }

}

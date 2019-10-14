package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import seedu.address.model.entity.body.Body;

/**
 * A ui for the line chart that is displayed at the bottom of the dashboard.
 */
public class LineChartPanel extends UiPart<Region> {

    private static final String FXML = "LineChartPanel.fxml";
    private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private static final int WINDOW_SIZE = 10;
    // this is used to display time in HH:mm:ss format
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d");
    final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private XYChart.Series<String, Number> series = new XYChart.Series<>();
    private Map<Date, Number> freqByDate = new TreeMap<>();
    private ObservableList<Body> bodyList;

    @FXML
    private TextArea resultDisplay;

    public LineChartPanel(ObservableList<Body> bodyList) {
        super(FXML);
        this.bodyList = bodyList;
    }

    public LineChart getLineChart() {
        initialiseTreeMap();
        initialiseLineChart();
        updateSeries();
        updateUponChange();
        return lineChart;
    }

    /**
     * Initialises the line chart with a title, axes labels, and a series.
     */
    private void initialiseLineChart() {
        //defining the axes
        xAxis.setLabel("Day");
        yAxis.setLabel("Number");

        //creating the line chart with two axis created above
        lineChart.setTitle("Bodies Admitted");
        lineChart.setAnimated(false);

        //defining a series to display data
        series.setName("Data Series");

        // add series to chart
        lineChart.getData().add(series);

        // add hashmap data to series;
    }

    /**
     * Updates the series whenever a body is removed or added.
     */
    private void updateUponChange() {
        bodyList.addListener((ListChangeListener<Body>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    updateBodyAdded(c);
                    updateSeries();
                }
                if (c.wasRemoved()) {
                    updateBodyRemoved(c);
                    updateSeries();
                }
            }
        });
    }

    /**
     * Initialises the tree map to include the latest 10 days.
     */
    private void initialiseTreeMap() {
        // Fill in the missing dates
        Date now = new Date();
        Date tenDaysAgo = new Date(now.getTime() - (10 * DAY_IN_MS));
        for (Date date = now; date.after(tenDaysAgo); date = new Date(date.getTime() - DAY_IN_MS)) {
            freqByDate.putIfAbsent(date, 0);
        }
    }

    /**
     * Update the tree map upon a body being added - the frequency (value) associated with the date of admission (key)
     * will increase by one.
     * @param c Change in the ObservableList of bodies.
     */
    private void updateBodyAdded(ListChangeListener.Change<? extends Body> c) {
        Date now = c.getAddedSubList().get(0).getDateOfAdmission();
        Number oldFreq = freqByDate.getOrDefault(now, 0);
        int newFreq = oldFreq.intValue() + 1;
        freqByDate.put(now, newFreq);
    }

    /**
     * Update the tree map upon a body being deleted - the frequency (value) associated with the date of admission (key)
     * will decrease by one.
     * @param c Change in the ObservableList of bodies.
     */
    private void updateBodyRemoved(ListChangeListener.Change<? extends Body> c) {
        Date now = c.getRemoved().get(0).getDateOfAdmission();
        Number oldFreq = freqByDate.getOrDefault(now, 0);
        int newFreq = oldFreq.intValue() - 1;
        freqByDate.put(now, newFreq);
    }

    /**
     * Clears series data and populate series again with the current tree map keys and values.
     */
    private void updateSeries() {
        // clear previous data
        series.getData().clear();
        // Update series based on dates in current hashmap
        freqByDate.forEach((date, freq) -> {
            series.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(date), freq));
            if (series.getData().size() > WINDOW_SIZE) {
                series.getData().remove(0);
            }
        });
    }

}

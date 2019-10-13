package seedu.address.ui;

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
import seedu.address.model.entity.worker.Worker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;


/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LineChartPanel extends UiPart<Region> {

    private static final String FXML = "LineChartPanel.fxml";
    private ScheduledExecutorService scheduledExecutorService;
    private ObservableList<Worker> bodyList;
    // this is used to display time in HH:mm:ss format
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d");
    final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    final int WINDOW_SIZE = 10;
    private XYChart.Series<String, Number> series = new XYChart.Series<>();
    private HashMap<String, Number> freqByDate = new HashMap<>();

    @FXML
    private TextArea resultDisplay;

    public LineChartPanel(ObservableList<Worker> bodyList) {
        super(FXML);
        this.bodyList = bodyList;
    }

    public LineChart getLineChart() {
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

        bodyList.addListener((ListChangeListener<Worker>) c -> {
            while (c.next()) {

                if (c.wasAdded()) {
                    Date now = c.getAddedSubList().get(0).getDateOfBirth();
                    String dateOnXAxis = simpleDateFormat.format(now);
                    Number oldFreq = freqByDate.getOrDefault(dateOnXAxis,0);
                    int newFreq = oldFreq.intValue() + 1;
                    freqByDate.put(dateOnXAxis, newFreq);
                }

                if (c.wasRemoved()) {
                    Date now = c.getRemoved().get(0).getDateOfBirth();
                    String dateOnXAxis = simpleDateFormat.format(now);
                    Number oldFreq = freqByDate.getOrDefault(dateOnXAxis,0);
                    int newFreq = oldFreq.intValue() - 1;
                    freqByDate.put(dateOnXAxis, newFreq);
                }
                reinitialiseChart();
            }
        });

        return lineChart;
    }

    public void reinitialiseChart() {
        series.getData().clear();
        freqByDate.forEach((date, freq) -> {
            series.getData().add(new XYChart.Data<>(date, freq));
            if (series.getData().size() > WINDOW_SIZE) {
                series.getData().remove(0);
            }
        });
    }
}

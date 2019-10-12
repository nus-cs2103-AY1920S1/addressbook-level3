package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LineChartPanel extends UiPart<Region> {

    private static final String FXML = "LineChartPanel.fxml";
    private ScheduledExecutorService scheduledExecutorService;
    private ObservableList<Worker> bodyList;
    // this is used to display time in HH:mm:ss format
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    final int WINDOW_SIZE = 10;

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

        //defining a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        // add series to chart
        lineChart.getData().add(series);

        bodyList.addListener((ListChangeListener<Worker>) c -> {
            while (c.next()) {
                Date now = new Date();
                if (c.wasAdded()) {
                    series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), 10));
                }
                if (c.wasRemoved()) {
                    series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), 5));
                }
                if (series.getData().size() > WINDOW_SIZE) {
                    series.getData().remove(0);
                }
            }
        });

//        // setup a scheduled executor to periodically put data into the chart
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//
//        // put dummy data onto graph per second
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            // get a random integer between 0-10
//            Integer random = ThreadLocalRandom.current().nextInt(10);
//
//            // Update the chart
//            Platform.runLater(() -> {
//                // get current time
//                Date now = new Date();
//                // put random number with current time
//                for (int i = 0; i < bodyList.size(); i++) {
//                    series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), random));
//                }
//                // cut the chart off after specific length
//                if (series.getData().size() > WINDOW_SIZE) {
//                    series.getData().remove(0);
//                }
//            });
//        }, 0, 1, TimeUnit.MINUTES);
        return lineChart;
    }

    public void shutDownScheduledExecutor() {
        this.scheduledExecutorService.shutdownNow();
    }
}

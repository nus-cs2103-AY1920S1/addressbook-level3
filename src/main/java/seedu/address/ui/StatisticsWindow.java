package seedu.address.ui;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * pop up window that will be used to display statistics calculated
 */
public class StatisticsWindow extends UiPart<Stage> {
    private static final String FXML = "StatisticsWindow.fxml";
    private XYChart.Series<String, Number> axisSeries;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Number> testChart;
    @FXML
    private Label valueLabel;
    @FXML
    private Label startingDateLabel;
    @FXML
    private Label endingDateLabel;


    /**
     * Create a new Statistic window
     * @param root Stage in which the window will use
     */
    public StatisticsWindow(Stage root, Optional<XYChart.Series<String, Number>> axisSeriesOptional) {
        super(FXML, root);
        if (axisSeriesOptional.isPresent()) {
            this.axisSeries = axisSeriesOptional.get();
            buildChart();
        }
    }

    /**
     * utility method to create statistic window with the data in user input date mode
     * @param statsLabel the title of the stats
     * @param axisSeries  graph data from logic
     */
    public StatisticsWindow(String statsLabel, XYChart.Series<String, Number> axisSeries) {
        this(new Stage(), Optional.of(axisSeries));
        this.testChart.setTitle(statsLabel);
        setMiscStatsLabel(statsLabel, axisSeries);

    }

    private void setMiscStatsLabel(String statsLabel, XYChart.Series<String, Number> axisSeries) {
        this.valueLabel.setText("Displaying monthly view of " + statsLabel);
        ObservableList<XYChart.Data<String, Number>> axisSeriesData = axisSeries.getData();
        XYChart.Data<String, Number> firstData = axisSeriesData
                .stream()
                .findFirst().get();
        XYChart.Data<String, Number> lastData = axisSeriesData
                .stream()
                .reduce((first, second) -> second).get();
        this.startingDateLabel.setText(
                "Initial value of " + statsLabel
                        + "\n" + firstData.getYValue() + " on " + firstData.getXValue()
        );
        this.startingDateLabel.setWrapText(true);
        this.endingDateLabel.setText(
                "Final value of " + statsLabel + "\n"
                        + lastData.getYValue() + " on " + lastData.getXValue()
        );
        this.endingDateLabel.setWrapText(true);
    }

    /**
     * intialize the chart in the fxml file
     */
    private void buildChart() {
        //Defining the x axis
        xAxis.setLabel("Months");
        //Defining the y axis
        yAxis.setLabel("Value in $");
        this.testChart.getData().add(axisSeries);
        this.testChart.setLegendVisible(false);
        this.testChart.setCreateSymbols(false);
    }

    /**
     * Shows the Statistics Window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the statistics Window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistics Window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the statistics Window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}

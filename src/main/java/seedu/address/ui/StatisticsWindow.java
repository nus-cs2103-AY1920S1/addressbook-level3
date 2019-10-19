package seedu.address.ui;

import javafx.fxml.FXML;

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

    @FXML
    private Label statsLabel;
    @FXML
    private Label testLabel;
    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    private LineChart<Number, Number> testChart;

    /**
     * Create a new Statistic window
     * @param root Stage in which the window will use
     */
    public StatisticsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * utility method to create statistic window with the data
     * @param statisticsResult the result of the statistic
     * @param statsLabel the title of the stats
     */
    public StatisticsWindow(String statisticsResult, String statsLabel) {
        this(new Stage());
        //this.testLabel.setText(statisticsResult);
        //this.statsLabel.setText(statsLabel);
        this.testChart.setTitle(statsLabel);
    }

    private void buildChart() {
        //Defining the x axis
        xAxis.setLabel("testXLabel");
        //Defining the y axis
        yAxis.setLabel("testYLabel");
        this.testChart.getData().add(getSeries());
    }

    private XYChart.Series<Number, Number> getSeries() {
        //System.out.println("TESTT");
        XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();
        series.getData().add(new XYChart.Data<Number,Number>(10,5));
        series.getData().add(new XYChart.Data<Number,Number>(20,10));
        series.getData().add(new XYChart.Data<Number,Number>(30,20));
        series.getData().add(new XYChart.Data<Number,Number>(40,50));
        series.getData().add(new XYChart.Data<Number,Number>(50,40));
        return series;
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
        buildChart();
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

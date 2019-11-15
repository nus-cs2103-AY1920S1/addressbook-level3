package seedu.address.financialtracker.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.PageManager;
import seedu.address.ui.UiPart;

/**
 * Controller for a help page.
 */
public class FinancialTrackerSummaryWindow extends UiPart<Stage> {

    private static final String FXML = "FinancialTrackerSummaryWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label title;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    private FinancialTrackerSummaryWindow(Stage root, ObservableList<PieChart.Data> pieChartData,
                                          ArrayList<XYChart.Series> seriesArray) {
        super(FXML, root);
        this.pieChart.setData(pieChartData);
        this.pieChart.setLabelLineLength(10);
        this.barChart.getData().addAll(seriesArray);

        // Adapted from pohlinwei
        getRoot().focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                delay.setOnFinished(event -> getRoot().hide());
                delay.play();
            }
        }));

        getRoot().setOnShowing(event -> {
            getRoot().setHeight(600);
            getRoot().setWidth(800);
            // centralise
            getRoot().setX(PageManager.getXPosition() - 400);
            getRoot().setY(PageManager.getYPosition() - 300);
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public FinancialTrackerSummaryWindow(ObservableList<PieChart.Data> pieChartData,
                                         ArrayList<XYChart.Series> seriesArray) {
        this(new Stage(), pieChartData, seriesArray);
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing summary page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }
}

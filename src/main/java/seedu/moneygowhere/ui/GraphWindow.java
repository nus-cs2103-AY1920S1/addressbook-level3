package seedu.moneygowhere.ui;

import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.model.spending.Date;

/**
 * Controller for a graph page
 */
public class GraphWindow extends UiPart<Stage> {

    public static final String GRAPH_MESSAGE = "Spending of current month";

    private static final Logger logger = LogsCenter.getLogger(GraphWindow.class);
    private static final String FXML = "GraphWindow.fxml";

    @FXML
    private Pane paneView;

    /**
     * Creates a new GraphWindow.
     *
     * @param root Stage to use as the root of the GraphWindow.
     */
    public GraphWindow(Stage root) {
        super(FXML, root);
        root.sizeToScene();
    }

    /**
     * Creates a new GraphWindow.
     */
    public GraphWindow() {
        this(new Stage());
    }

    public void loadData(Map<Date, Double> graphData) {
        paneView.getChildren().clear();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount spent ($)");
        LineChart<String, Number> spendingChart = new LineChart<>(xAxis, yAxis);
        spendingChart.setTitle("Current month's spending");
        XYChart.Series series = new XYChart.Series();
        series.setName("spending");
        for (Map.Entry i : graphData.entrySet()) {
            series.getData().add(new XYChart.Data<String, Number>(i.getKey().toString(), Math.round((Double)i.getValue())));
        }
        spendingChart.getData().add(series);
        spendingChart.setMinWidth(600);
        spendingChart.setMinHeight(600);
        spendingChart.setMaxWidth(1200);
        spendingChart.setMaxHeight(600);
        paneView.getChildren().add(spendingChart);
        paneView.setStyle("-fx-background-color: white");
    }

    /**
     * Shows the graph window.
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
        logger.fine("Showing graph page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the graph window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the graph window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the graph window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}

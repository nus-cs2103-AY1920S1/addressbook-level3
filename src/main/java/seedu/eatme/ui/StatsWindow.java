package seedu.eatme.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import seedu.eatme.model.statistics.Statistics;

/**
 * Creates the statistics window that displays multiple statistics.
 */
public class StatsWindow extends UiPart<Stage> {
    private static final String FXML = "StatsWindow.fxml";

    private Statistics statistics;

    @FXML
    private HBox chartDisplayView;

    @FXML
    private HBox numDisplayView;

    public StatsWindow(Stage root) {
        super(FXML, root);

        root.setOnCloseRequest((WindowEvent event) -> {
            numDisplayView.getChildren().clear();
            chartDisplayView.getChildren().clear();
        });
    }

    public StatsWindow() {
        this(new Stage());
    }

    /**
     * Shows the statistics window.
     */
    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Checks if the statistics window is currently showing.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses the statistics window if it's already open.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Initialises the charts and graphs.
     * @param statistics contains the data used to generate the charts and graphs.
     */
    public void initStats(Statistics statistics) {
        numDisplayView.getChildren().clear();
        chartDisplayView.getChildren().clear();

        initCharts(statistics);
        initNum(statistics);
    }

    /**
     * Creates the graphs and charts needed for the statistics.
     */
    private void initCharts(Statistics statistics) {
        CustomLayeredGraph graph = new CustomLayeredGraph("How much you spent per category",
                statistics.graphCategoryTotalExpense,
                statistics.graphCategoryAvgExpense);
        CustomPieChart pieChart = new CustomPieChart("How many times visited per category",
                statistics.chartCategoryTotalVisited);

        chartDisplayView.getChildren().addAll(graph.getRoot(), pieChart.getRoot());
        chartDisplayView.setStyle("-fx-padding: 20 0 0 0");
    }

    /**
     * Creates other miscellaneous statistics that do not require graph or chart representation.
     */
    private void initNum(Statistics statistics) {
        TextFlow mostExpEateries = new TextFlow();
        mostExpEateries.setTextAlignment(TextAlignment.CENTER);
        Text titleMostExp = new Text("Top 3 eateries you spent the most at\n");
        titleMostExp.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
        mostExpEateries.getChildren().add(titleMostExp);

        for (int i = 0; i < statistics.mostExpEatery.size(); i++) {
            mostExpEateries.getChildren().add(new Text(String.format("%d. %s\n", i + 1,
                    statistics.mostExpEatery.get(i).getName())));
        }

        TextFlow leastExpEateries = new TextFlow();
        leastExpEateries.setTextAlignment(TextAlignment.CENTER);
        leastExpEateries.setStyle(String.format("-fx-padding: 0 20 0 20"));
        Text titleLeastExp = new Text("Top 3 eateries you spent the least at\n");
        titleLeastExp.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
        leastExpEateries.getChildren().add(titleLeastExp);

        for (int i = 0; i < statistics.leastExpEatery.size(); i++) {
            leastExpEateries.getChildren().add(new Text(String.format("%d. %s\n", i + 1,
                    statistics.leastExpEatery.get(i).getName())));
        }

        TextFlow mostVisitedEateries = new TextFlow();
        mostVisitedEateries.setTextAlignment(TextAlignment.CENTER);
        Text titleMostVisited = new Text("Top 3 eateries you visited the most\n");
        titleMostVisited.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
        mostVisitedEateries.getChildren().add(titleMostVisited);

        for (int i = 0; i < statistics.mostVisitedEatery.size(); i++) {
            mostVisitedEateries.getChildren().add(new Text(String.format("%d. %s\n", i + 1,
                    statistics.mostVisitedEatery.get(i).getName())));
        }

        TextFlow leastVisitedEateries = new TextFlow();
        leastVisitedEateries.setTextAlignment(TextAlignment.CENTER);
        leastVisitedEateries.setStyle(String.format("-fx-padding: 0 0 0 20"));
        Text titleLeastVisited = new Text("Top 3 eateries you visited the least\n");
        titleLeastVisited.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
        leastVisitedEateries.getChildren().add(titleLeastVisited);

        for (int i = 0; i < statistics.leastVisitedEatery.size(); i++) {
            leastVisitedEateries.getChildren().add(new Text(String.format("%d. %s\n", i + 1,
                    statistics.leastVisitedEatery.get(i).getName())));
        }

        numDisplayView.getChildren().addAll(mostExpEateries, leastExpEateries, mostVisitedEateries,
                leastVisitedEateries);
        numDisplayView.setStyle("-fx-padding: 0 10 0 10");
    }
}

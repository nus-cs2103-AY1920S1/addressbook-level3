package seedu.address.ui;

import java.util.HashMap;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import seedu.address.model.statistics.Statistics.StatisticsType;
import seedu.address.model.statistics.StatisticsList;

/**
 * Creates the statistics window that displays multiple statistics.
 */
public class StatsWindow extends UiPart<Stage> {
    private static final String FXML = "StatsWindow.fxml";

    private StatisticsList statisticsList;

    @FXML
    private FlowPane statsDisplayView;

    public StatsWindow(Stage root) {
        super(FXML, root);

        root.setOnCloseRequest((WindowEvent event) -> {
            statsDisplayView.getChildren().clear();
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
     * @param statisticsList contains the data used to generate the charts and graphs.
     */
    public void initStats(StatisticsList statisticsList) {
        HashMap<StatisticsType, TreeMap<? extends Object, Double>> stats = statisticsList.getStatisticsList();

        if (stats.containsKey(StatisticsType.GRAPH_OVERALL_CATEGORY_TOTAL)) {
            CustomGraph graph = new CustomGraph("How much you spent on food per category",
                    stats.get(StatisticsType.GRAPH_OVERALL_CATEGORY_TOTAL),
                    stats.get(StatisticsType.GRAPH_OVERALL_CATEGORY_TOTAL));
            statsDisplayView.getChildren().add(graph.getRoot());
        }
    }
}

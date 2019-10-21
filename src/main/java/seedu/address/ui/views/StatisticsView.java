package seedu.address.ui.views;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.task.TaskStatus;
import seedu.address.ui.UiPart;

public class StatisticsView extends UiPart<Region> {
    private static final String FXML = "Statistics.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsView.class);

    @FXML
    private PieChart taskByStatus;
    private PieChart memberByTasks;

    public StatisticsView(Statistics stats) {
        super(FXML);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("NOT STARTED", stats.getPortionTasksByStatus().get(TaskStatus.UNBEGUN)),
        new PieChart.Data("DOING", 2),
        new PieChart.Data("DONE", 1));

        taskByStatus.setData(pieChartData);
    }

}

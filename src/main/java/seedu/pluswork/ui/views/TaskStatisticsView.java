package seedu.pluswork.ui.views;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.ui.UiPart;

public class TaskStatisticsView extends UiPart<Region> {
    private static final String FXML = "TaskStatistics.fxml";
    private final Logger logger = LogsCenter.getLogger(MemberStatisticsView.class);

    @FXML
    private PieChart taskByStatus;
    @FXML
    private ListView<Task> taskListView;

    public TaskStatisticsView(Statistics stats, ObservableList<Task> tasks) {
        super(FXML);
        //For list of tasks
        taskListView.setItems(tasks);
        taskListView.setCellFactory(listView -> new TaskListViewCell());

        //For PieChart taskByStatus
        ObservableList<PieChart.Data> taskByStatusData = stats.getPieChartDataForTasksByStatus();

        taskByStatus.setData(taskByStatusData);

        taskByStatusData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", Math.round(data.getPieValue()), " tasks"
                        )
                )
        );

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCardForStats(task, getIndex() + 1).getRoot());
            }
        }
    }
}

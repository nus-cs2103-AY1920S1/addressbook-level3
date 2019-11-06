package seedu.address.ui.views;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;
import seedu.address.ui.UiPart;

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
        ObservableList<PieChart.Data> taskByStatusData = FXCollections.observableArrayList(
                new PieChart.Data("NOT STARTED", stats.getPortionTasksByStatus().get(TaskStatus.UNBEGUN)),
                new PieChart.Data("DOING", stats.getPortionTasksByStatus().get(TaskStatus.DOING)),
                new PieChart.Data("DONE", stats.getPortionTasksByStatus().get(TaskStatus.DONE)));

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

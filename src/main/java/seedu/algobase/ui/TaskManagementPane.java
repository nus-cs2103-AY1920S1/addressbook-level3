package seedu.algobase.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.task.Task;

/**
 * Panel containing the list of tasks.
 */
public class TaskManagementPane extends UiPart<Region> {
    private static final String FXML = "TaskManagementPane.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskManagementPane.class);
    private ObservableList<Data> taskProgress = FXCollections.observableArrayList();

    @FXML
    private Label currentPlan;
    @FXML
    private ListView<Task> taskListView;
    @FXML
    private PieChart taskProgressChart;

    public TaskManagementPane(ObservableList<Task> taskList,
                       ObservableStringValue plan,
                       ObservableIntegerValue doneCount,
                       ObservableIntegerValue undoneCount,
                       ObservableIntegerValue taskCount
    ) {
        super(FXML);
        if (!plan.getValue().equals("")) {
            currentPlan.setText("Current Plan: " + plan.getValue());
        } else {
            currentPlan.setText("No Current Plan");
        }
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        taskProgressChart.setData(getChartData(doneCount.getValue().intValue(), undoneCount.getValue().intValue()));
        taskProgressChart.setClockwise(true);
        taskProgressChart.setLabelsVisible(false);
        taskProgressChart.setLegendVisible(taskCount.getValue().intValue() != 0);
        taskProgressChart.setStartAngle(90);
        addListenerForPlanName(plan);
        addListenerForPieChart(doneCount, undoneCount, taskCount);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    static class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Obtains data for pie chart.
     */
    private ObservableList<Data> getChartData(int solvedCount, int unsolvedCount) {
        taskProgress.addAll(new PieChart.Data("Done", solvedCount),
            new PieChart.Data("Undone", unsolvedCount));
        return taskProgress;
    }

    /**
     * Adds a listener to watch for changes for plan name.
     *
     * @param s The observable plan name string.
     */
    private void addListenerForPlanName(ObservableStringValue s) {
        s.addListener((observable, oldValue, newValue) -> {
            logger.info("Current plan is updated to [" + newValue + "]");
            if (!newValue.equals("")) {
                currentPlan.setText("Current Plan: " + newValue);
            } else {
                currentPlan.setText("No Current Plan");
            }
        });
    }

    /**
     * Adds a listener to watch for changes for task progress.
     *
     * @param doneCount The observable done count.
     * @param undoneCount The observable undone count.
     */
    private void addListenerForPieChart(
        ObservableIntegerValue doneCount,
        ObservableIntegerValue undoneCount,
        ObservableIntegerValue taskCount
    ) {
        doneCount.addListener((observable, oldValue, newValue) -> {
            logger.info("Current done count is updated to [" + newValue + "]");
            for (Data d : taskProgress) {
                if (d.getName().equals("Done")) {
                    d.setPieValue(newValue.intValue());
                }
            }
        });
        undoneCount.addListener((observable, oldValue, newValue) -> {
            logger.info("Current undone count is updated to [" + newValue + "]");
            for (Data d : taskProgress) {
                if (d.getName().equals("Undone")) {
                    d.setPieValue(newValue.intValue());
                }
            }
        });
        taskCount.addListener((observable, oldValue, newValue) -> {
            logger.info("Current total task count is updated to [" + newValue + "]");
            taskProgressChart.setLegendVisible(newValue.intValue() != 0);
        });
    }

}

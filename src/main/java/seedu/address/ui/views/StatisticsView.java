package seedu.address.ui.views;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.member.Member;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.task.TaskStatus;
import seedu.address.ui.UiPart;

public class StatisticsView extends UiPart<Region> {
    private static final String FXML = "Statistics.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsView.class);

    @FXML
    private PieChart memberByTasks;

    @FXML
    private PieChart taskByStatus;

    public StatisticsView(Statistics stats, List<Member> members) {
        super(FXML);
        ObservableList<PieChart.Data> taskByStatusData = FXCollections.observableArrayList(
        new PieChart.Data("NOT STARTED", stats.getPortionTasksByStatus().get(TaskStatus.UNBEGUN)),
        new PieChart.Data("DOING", stats.getPortionTasksByStatus().get(TaskStatus.DOING)),
        new PieChart.Data("DONE", stats.getPortionTasksByStatus().get(TaskStatus.DONE)));

        taskByStatus.setData(taskByStatusData);

        ObservableList<PieChart.Data> memberByTasksData = FXCollections.observableArrayList();

        for (Member mem : members) {
            PieChart.Data toBeAdded = new PieChart.Data(mem.getName().toString(), stats.getPortionMembersByTasks().get(mem));
            memberByTasksData.add(toBeAdded);
        }

        memberByTasks.setData(memberByTasksData);
    }
}

package seedu.pluswork.ui.views;

import java.util.List;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.ui.UiPart;

public class MemberStatisticsView extends UiPart<Region> {
    private static final String FXML = "MemberStatistics.fxml";
    private final Logger logger = LogsCenter.getLogger(MemberStatisticsView.class);

    @FXML
    private PieChart memberByTasks;
    @FXML
    private PieChart memberByInv;

    public MemberStatisticsView(Statistics stats, List<Member> members) {
        super(FXML);

        //For PieChart memberByTasks
        ObservableList<PieChart.Data> memberByTasksData = FXCollections.observableArrayList();
        for (Member mem : members) {
            PieChart.Data toBeAdded = new PieChart.Data(mem.getName().toString(), stats.getPortionMembersByTasks().get(mem));
            memberByTasksData.add(toBeAdded);
        }

        memberByTasks.setData(memberByTasksData);

        memberByTasksData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", Math.round(data.getPieValue()), " tasks"
                        )
                )
        );

        //For PieChart memberByInv
        ObservableList<PieChart.Data> memberByInvData = FXCollections.observableArrayList();
        for (Member mem : members) {
            PieChart.Data toBeAdded = new PieChart.Data(mem.getName().toString(), stats.getPortionMembersByInv().get(mem));
            memberByInvData.add(toBeAdded);
        }

        memberByInv.setData(memberByInvData);

        memberByInvData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", Math.round(data.getPieValue()), " items"
                        )
                )
        );
    }
}

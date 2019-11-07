package seedu.pluswork.model.statistics;

import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

public class Statistics {
    private final List<Member> members;
    private final List<Task> tasks;
    private final List<TasMemMapping> tasMem;
    private final List<InvMemMapping> invMem;
    private final HashMap<TaskStatus, Integer> portionTasksByStatus = new HashMap<>();
    private final HashMap<Member, Integer> portionMemberByTasks = new HashMap<>();
    private final HashMap<Member, Integer> portionMemberByInv = new HashMap<>();

    public Statistics(List<Member> members, List<Task> tasks, List<TasMemMapping> tasMem, List<InvMemMapping> invMem) {
        requireAllNonNull(members, tasks, tasMem, invMem);
        this.members = members;
        this.tasks = tasks;
        this.tasMem = tasMem;
        this.invMem = invMem;
    }


    public List<Task> getTaskList() {
        return this.tasks;
    }

    public List<Member> getMemberList() {
        return this.members;
    }

    public List<TasMemMapping> getTasMemList() {
        return this.tasMem;
    }

    public List<InvMemMapping> getInvMemList() {
        return this.invMem;
    }


    //only do if the statistics object is unique
    public void doCalculations() {
        for (int i = 0; i < members.size(); i++) {
            int numTasks = 0;
            for (TasMemMapping mapping : tasMem) {
                if (mapping.hasMember(i)) {
                    numTasks++;
                }
            }
            portionMemberByTasks.put(members.get(i), numTasks);
        }

        for (int i = 0; i < members.size(); i++) {
            int numItems = 0;
            for (InvMemMapping mapping : invMem) {
                if (mapping.hasMember(i)) {
                    numItems++;
                }
            }
            portionMemberByInv.put(members.get(i), numItems);
        }

        int unbegun = 0;
        int doing = 0;
        int done = 0;

        for (Task task : tasks) {
            switch (task.getTaskStatus()) {
                case UNBEGUN:
                    unbegun++;
                    break;
                case DOING:
                    doing++;
                    break;
                case DONE:
                    done++;
                    break;
                default:
                    assert (false);
            }
        }

        portionTasksByStatus.put(TaskStatus.UNBEGUN, unbegun);
        portionTasksByStatus.put(TaskStatus.DOING, doing);
        portionTasksByStatus.put(TaskStatus.DONE, done);
    }

    public HashMap<Member, Integer> getPortionMembersByTasks() {
        return portionMemberByTasks;
    }

    public HashMap<Member, Integer> getPortionMembersByInv() {
        return portionMemberByInv;
    }

    public HashMap<TaskStatus, Integer> getPortionTasksByStatus() {
        return portionTasksByStatus;
    }

    public ObservableList<PieChart.Data> getPieChartDataForTasksByStatus() {
        ObservableList<PieChart.Data> taskByStatusData = FXCollections.observableArrayList(
                new PieChart.Data("NOT STARTED", getPortionTasksByStatus().get(TaskStatus.UNBEGUN)),
                new PieChart.Data("DOING", getPortionTasksByStatus().get(TaskStatus.DOING)),
                new PieChart.Data("DONE", getPortionTasksByStatus().get(TaskStatus.DONE)));

        return taskByStatusData;
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Statistics)) {
            return false;
        }

        Statistics otherStats = (Statistics) other;

        return otherStats != null
                && tasks.equals(otherStats.getTaskList())
                && invMem.equals(otherStats.getInvMemList())
                && tasMem.equals(otherStats.getTasMemList())
                && members.equals(otherStats.getMemberList());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, members, tasMem, invMem);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Statistics for Project Manager");
        return builder.toString();
    }
}

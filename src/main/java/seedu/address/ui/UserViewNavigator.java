package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.Logic;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.ui.views.MemberListPanel;
import seedu.address.ui.views.InventoryListPanel;
import seedu.address.ui.views.ProjectDashboardView;
import seedu.address.ui.views.MemberStatisticsView;
import seedu.address.ui.views.SettingsView;
import seedu.address.ui.views.TaskListPanel;
import seedu.address.ui.views.TaskStatisticsView;

/**
 * Utility class for controlling navigation between user views.
 * Stores the different views here.
 *
 * Main access of the navigator is from {@code UserViewMain}.
 */
public class UserViewNavigator {

    /** The main application layout controller. */
    private static UserViewController userViewController;

    /** The views to switch between **/
    private ProjectDashboardView projectDashboardView;
    private TaskListPanel taskListPanel;
    private InventoryListPanel inventoryListPanel;
    private MemberStatisticsView memberStatsView;
    private TaskStatisticsView taskStatsView;
    private SettingsView settingsView;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param userViewController the dynamic application layout controller.
     */
    public static void setMainController(UserViewController userViewController) {
        UserViewNavigator.userViewController = userViewController;
    }


    /**
     * Relays to the controller to swap current user view with dashboard.
     * @param logic to access the task data
     */
    public void loadDashboard(Logic logic) {
        ProjectDashboardView projectDashboardView = new ProjectDashboardView(logic.getFilteredTaskListNotStarted(),
                logic.getFilteredTaskListDoing(), logic.getFilteredTaskListDone(),
                logic.getFilteredTaskListByDeadline());
        userViewController.setUserView(projectDashboardView);
    }

    /**
     * Relays to controller to swap current user view with task list.
     * @param logic to access task data
     */
    public void loadTaskListView(Logic logic) {
        TaskListPanel taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        userViewController.setUserView(taskListPanel);
    }


    // TODO get filtered member list from logic interface
    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadMemberListView(Logic logic) {
        MemberListPanel memberListPanel = new MemberListPanel(logic.getProjectDashboard().getMemberList());
        userViewController.setUserView(memberListPanel);
    }

    /**
     * Relays to controller to swap current user view with inventory list.
     * @param logic to access inventory data
     */
    public void loadInventoriesListView(Logic logic) {
        ObservableList<InvMemMapping>invMemMappings = logic.getProjectDashboard().getInvMemMappingList();
        ObservableList<InvTasMapping>invTasMappings = logic.getProjectDashboard().getInvTasMappingList();
        ArrayList<InvMemMapping>invMemMap = new ArrayList<>(invMemMappings);
        ArrayList<InvTasMapping>invTasMap = new ArrayList<>(invTasMappings);
        ArrayList<Inventory>inventories = new ArrayList<>(logic.getFilteredInventoryList());
        ArrayList<Member>members = new ArrayList<>(logic.getFilteredMemberList());
        ArrayList<Task>tasks = new ArrayList<>(logic.getFilteredTaskList());
        inventoryListPanel = new InventoryListPanel(inventories, members, tasks, invMemMap, invTasMap);
        userViewController.setUserView(inventoryListPanel);
    }

    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadMemberStatsView(Logic logic) {
        memberStatsView = new MemberStatisticsView(logic.getStatistics(), logic.getFilteredMemberList());
        userViewController.setUserView(memberStatsView);
    }

    /**
     * Relays to controller to swap current user view with task list.
     * @param logic to access task data
     */
    public void loadTaskStatsView(Logic logic) {
        taskStatsView = new TaskStatisticsView(logic.getStatistics(), logic.getFilteredTaskList());
        userViewController.setUserView(taskStatsView);
    }

    /**
     * Relays to the controller to swap current user view with settings view.
     * @param logic to access settings data
     */
    public void loadSettingsView(Logic logic) {
        settingsView = new SettingsView(logic.getCurrentTheme(), logic.getClockFormat());
        userViewController.setUserView(settingsView);
    }

}

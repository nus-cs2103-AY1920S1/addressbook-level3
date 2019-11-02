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

import java.util.List;

import javafx.collections.FXCollections;
import seedu.address.commons.Keywords;
import seedu.address.logic.Logic;
import seedu.address.model.mapping.TasMemMapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.ui.views.CommandListPanel;
import seedu.address.ui.views.IndivMemberCard;
import seedu.address.ui.views.InventoryListPanel;
import seedu.address.ui.views.MemberListPanel;
import seedu.address.ui.views.MemberStatisticsView;
import seedu.address.ui.views.ProjectDashboardView;
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
    private CommandListPanel commandListPanel;

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
        TaskListPanel taskListPanel = new TaskListPanel(logic.getProjectDashboard().getTaskList(),
                logic.getProjectDashboard().getMemberList(), logic.getProjectDashboard().getTasMemMappingList());
        userViewController.setUserView(taskListPanel);
    }


    // TODO get filtered member list from logic interface
    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadMemberListView(Logic logic) {
        MemberListPanel memberListPanel = new MemberListPanel(logic.getProjectDashboard().getMemberList(),
                logic.getProjectDashboard().getTaskList(), logic.getProjectDashboard().getTasMemMappingList());
        userViewController.setUserView(memberListPanel);
    }

    // TODO get filtered member list from logic interface
    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadSpecificMemberView(Logic logic) {
        List<Member> members = logic.getProjectDashboard().getMemberList();
        Member specificMember = members.get(members.size()-1);

        IndivMemberCard memberCard = new IndivMemberCard(specificMember, members.size());
        userViewController.setUserView(memberCard);
    }

    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadSetImageView(Logic logic) {
        List<Member> filteredMembers = logic.getFilteredMemberList();
        Member specificMember = filteredMembers.get(filteredMembers.size()-1);

        List<TasMemMapping> tasMemMappings = logic.getProjectDashboard().getTasMemMappingList();
        List<Task> tasks = logic.getProjectDashboard().getTaskList();
        List<Member> members = logic.getProjectDashboard().getMemberList();

        ArrayList<Task> specificTasks = new ArrayList<>();
        int memIndex = members.indexOf(specificMember) - 1;

        for (TasMemMapping mapping : tasMemMappings) {
            if (mapping.hasMember(memIndex)) {
                specificTasks.add(tasks.get(mapping.getTaskIndex()));
            }
        }

        IndivMemberCard memberCard = new IndivMemberCard(specificMember, filteredMembers.size(), specificTasks);
        userViewController.setUserView(memberCard);
    }

    // TODO get filtered member list from logic interface
    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadAssignView(Logic logic) {
        List<TasMemMapping> tasMemMappings = logic.getProjectDashboard().getTasMemMappingList();
        List<Task> tasks = logic.getProjectDashboard().getTaskList();
        List<Member> members = logic.getProjectDashboard().getMemberList();

        TasMemMapping mappingAdded = tasMemMappings.get(tasMemMappings.size()-1);
        Member specificMember = members.get(mappingAdded.getMemberIndex());

        ArrayList<Task> specificTasks = new ArrayList<>();

        for (TasMemMapping mapping : tasMemMappings) {
            if (mapping.hasMember(mappingAdded.getMemberIndex())) {
                specificTasks.add(tasks.get(mapping.getTaskIndex()));
            }
        }

        IndivMemberCard memberCard = new IndivMemberCard(specificMember,
                mappingAdded.getMemberIndex() + 1, specificTasks);
        userViewController.setUserView(memberCard);
    }

    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadFoundMembersView(Logic logic) {
        MemberListPanel memberListPanel = new MemberListPanel(logic.getFilteredMemberList(),
                logic.getProjectDashboard().getTaskList(),logic.getProjectDashboard().getTasMemMappingList());
        userViewController.setUserView(memberListPanel);
    }

    /**
     * Relays to controller to swap current user view with inventory list.
     * @param logic to access inventory data
     */
    public void loadInventoriesListView(Logic logic) {
        //ObservableList<InvMemMapping>invMemMappings = logic.getProjectDashboard().getInvMemMappingList();
        //ObservableList<InvTasMapping>invTasMappings = logic.getProjectDashboard().getInvTasMappingList();
        //ArrayList<InvMemMapping>invMemMap = new ArrayList<>(invMemMappings);
        //ArrayList<InvTasMapping>invTasMap = new ArrayList<>(invTasMappings);
        //ArrayList<Inventory>inventories = new ArrayList<>(logic.getFilteredInventoryList());
        //ArrayList<Member>members = new ArrayList<>(logic.getFilteredMemberList());
        //ArrayList<Task>tasks = new ArrayList<>(logic.getFilteredTaskList());
        //inventoryListPanel = new InventoryListPanel(inventories, members, tasks, invMemMap, invTasMap);
        inventoryListPanel = new InventoryListPanel(logic.getFilteredInventoryList(),
                                                    logic.getFilteredMemberList(),
                                                    logic.getFilteredTaskList(),
                                                    logic.getProjectDashboard().getInvMemMappingList(),
                                                    logic.getProjectDashboard().getInvTasMappingList());
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
    /**
     * Relays to the controller to swap current user view with help view.
     * @param logic to access settings data
     */
    public void loadHelpView(Logic logic) {
        commandListPanel = new CommandListPanel(FXCollections.observableList(Keywords.commandList));
        userViewController.setUserView(commandListPanel);
    }

}

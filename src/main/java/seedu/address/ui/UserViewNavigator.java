package seedu.address.ui;

import seedu.address.logic.Logic;
import seedu.address.ui.views.MemberListPanel;
import seedu.address.ui.views.ProjectDashboardView;
import seedu.address.ui.views.TaskListPanel;

import java.util.logging.Logger;

/**
 * Utility class for controlling navigation between user views.
 * Stores the different views here.
 *
 * Main access of the navigator is from {@code UserViewMain}.
 */
public class UserViewNavigator {

    /** The main application layout controller. */
    private static UserViewController userViewController;

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
        System.out.println("Trying to load a dashboard");
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
        System.out.println("Trying to load the task list!");
        TaskListPanel taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        userViewController.setUserView(taskListPanel);
    }

    /**
     * Relays to controller to swap current user view with member list.
     * @param logic to access task data
     */
    public void loadMemberListView(Logic logic) {
        MemberListPanel memberListPanel = new MemberListPanel(logic.getProjectDashboard().getMemberList());
        userViewController.setUserView(memberListPanel);
    }

}

package seedu.pluswork.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import seedu.pluswork.MainApp;
import seedu.pluswork.logic.Logic;


/**
 * Sets up the controller {@code UserViewController} to dynamically change user views.
 */
public class UserViewMain {
    private static final String FXML = "UserViewMain.fxml";
    private UserViewController userViewController;
    private UserViewNavigator userViewNavigator;
    private Logic logic;

    public UserViewMain(Logic logic) {
        this.logic = logic;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/" + FXML));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        userViewController = fxmlLoader.getController();
        assert userViewController != null;

        userViewNavigator = new UserViewNavigator();

        UserViewNavigator.setMainController(userViewController);
    }

    /**
     * Shows the viewer the dashboard.
     *
     * @return the dashboard layout
     */
    public Pane loadDashboard() {
        userViewNavigator.loadDashboard(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the list of tasks.
     *
     * @return {@code TaskListPanel}
     */
    public Pane loadTasks() {
        userViewNavigator.loadTaskListView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the list of tasks.
     *
     * @return {@code TaskListPanel}
     */
    public Pane loadFoundTasks() {
        userViewNavigator.loadFoundTaskView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the list of members.
     *
     * @return {@code MemberListPanel}
     */
    public Pane loadMembers() {
        userViewNavigator.loadMemberListView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the a specific member.
     *
     * @return {@code IndivMemberCard}
     */
    public Pane loadSpecificMember() {
        userViewNavigator.loadSpecificMemberView(logic);
        return userViewController.getCurrentView();
    }

    public Pane loadAssign() {
        userViewNavigator.loadAssignView(logic);
        return userViewController.getCurrentView();
    }

    public Pane loadFoundMembers() {
        userViewNavigator.loadFoundMembersView(logic);
        return userViewController.getCurrentView();
    }


    public Pane loadSetImage() {
        userViewNavigator.loadSetImageView(logic);
        return userViewController.getCurrentView();
    }

    public Pane loadMemberStats() {
        userViewNavigator.loadMemberStatsView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the list of inventories.
     *
     * @return {@code InventoryListPanel}
     */
    public Pane loadInventories() {
        userViewNavigator.loadInventoriesListView(logic);
        return userViewController.getCurrentView();
    }

    public Pane loadTaskStats() {
        userViewNavigator.loadTaskStatsView(logic);
        return userViewController.getCurrentView();
    }

    public Pane loadMeetingTimes() {
        userViewNavigator.loadMeetingTimesView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the current settings.
     *
     * @return {@code SettingsView}
     */
    public Pane loadSettingsView() {
        userViewNavigator.loadSettingsView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the commands.
     *
     * @return {@code CommandListPanel}
     */
    public Pane loadHelpView() {
        userViewNavigator.loadHelpView(logic);
        return userViewController.getCurrentView();
    }

}

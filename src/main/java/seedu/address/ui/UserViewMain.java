package seedu.address.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import seedu.address.MainApp;
import seedu.address.logic.Logic;


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
     * @return the task list pane
     */
    public Pane loadTasks() {
        userViewNavigator.loadTaskListView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the list of members.
     *
     * @return the task list pane
     */
    public Pane loadMembers() {
        userViewNavigator.loadMemberListView(logic);
        return userViewController.getCurrentView();
    }

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
     * @return the task list pane
     */
    public Pane loadInventories() {
        userViewNavigator.loadInventoriesListView(logic);
        return userViewController.getCurrentView();
    }

    public Pane loadTaskStats() {
        userViewNavigator.loadTaskStatsView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the current settings.
     *
     * @return the settings view
     */
    public Pane loadSettingsView() {
        userViewNavigator.loadSettingsView(logic);
        return userViewController.getCurrentView();
    }

    /**
     * Shows the user the commands.
     *
     * @return the help view
     */
    public Pane loadHelpView() {
        userViewNavigator.loadHelpView(logic);
        return userViewController.getCurrentView();
    }

}

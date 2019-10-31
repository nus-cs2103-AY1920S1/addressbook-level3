package seedu.address.ui;

import javafx.scene.layout.Pane;

/**
 * Communicates with {@code UserViewManager} to update the view based on user's command.
 * Updates each time a command is executed to refresh data.
 *
 */
public class UserViewUpdate {

    private Pane paneToRender;
    private UserViewMain userViewMain;

    public UserViewUpdate(Pane paneToRender, UserViewMain userViewMain) {
        this.paneToRender = paneToRender;
        this.userViewMain = userViewMain;
        paneToRender.getChildren().add(userViewMain.loadDashboard());
    }

    /**
     * Retrieves user command's preamble and retrieves the respective pane.
     * Clears the current view first and switches to the desired view.
     * If a command that does not require a view to be switched is entered,
     * the view persists.
     *
     * @param commandText raw command of user
     */
    public void parseUserCommand(String commandText) {
        String preamble = commandText.split(" ")[0];

        assert !(preamble.isEmpty());

        paneToRender.getChildren().clear();

        switch(preamble) {

        case "add-task":

        case "list-tasks":

        case "delete-task":

        case "edit-task":

        case "set-deadline":

        case "doing-task":

        case "done-task":

        case "find-task":
            paneToRender.getChildren().add(userViewMain.loadTasks());
            break;

        case "add-member":
            paneToRender.getChildren().add(userViewMain.loadSpecificMember());
            break;

        case "set-image":
            paneToRender.getChildren().add(userViewMain.loadSetImage());
            break;

        case "find-member":
            paneToRender.getChildren().add(userViewMain.loadFoundMembers());
            break;

        case "assign":
            paneToRender.getChildren().add(userViewMain.loadAssign());
            break;

        case "fire":

        case "edit-member":

        case "remove-member":

        case "list-members":
            paneToRender.getChildren().add(userViewMain.loadMembers());
            break;

        case "delete-inv":

        case "edit-inv":

        case "add-inv":

        case "list-inv":
            paneToRender.getChildren().add(userViewMain.loadInventories());
            break;



        case "member-stats":
           paneToRender.getChildren().add(userViewMain.loadMemberStats());
           break;


        case "task-stats":
            paneToRender.getChildren().add(userViewMain.loadTaskStats());
            break;

        case "settings":

        case "theme":

        case "clock":
            paneToRender.getChildren().add(userViewMain.loadSettingsView());
            break;

        case "clear":

        case "home":
            paneToRender.getChildren().add(userViewMain.loadDashboard());
            break;

        case "help":
            paneToRender.getChildren().add(userViewMain.loadHelpView());
        default:
            // show nothing (only exit has no case)
        }
    }
}

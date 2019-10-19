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

    // TODO update when inventory commands are added.
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

        case "list":

        case "delete-task":

        case "edit":

        case "set-deadline":

        case "find":
            paneToRender.getChildren().add(userViewMain.loadTasks());
            break;

        case "add-member":

        case "find-member":

        case "remove-member":

        case "list-members":
            paneToRender.getChildren().add(userViewMain.loadMembers());
            break;

        case "home":
            paneToRender.getChildren().add(userViewMain.loadDashboard());
            break;

        default:
            // show nothing (only exit has no case)
        }
    }
}

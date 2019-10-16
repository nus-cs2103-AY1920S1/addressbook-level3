package seedu.address.ui;

import javafx.scene.layout.Pane;

/**
 * Communicates with {@code UserViewManager} to update the view based on user's command.
 * Updates each time a command is executed to refresh data.
 *
 */
public class UserViewUpdate {

    private Pane paneToRender;
    private Pane mostRecentView;
    private UserViewMain userViewMain;

    public UserViewUpdate(Pane paneToRender, UserViewMain userViewMain) {
        this.paneToRender = paneToRender;
        this.userViewMain = userViewMain;
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

        mostRecentView = paneToRender;

        switch(preamble) {

        case "list":

        case "find":
            paneToRender = userViewMain.loadTasks();
            break;

        case "list-members":
            paneToRender = userViewMain.loadMembers();
            break;

        case "home":
        paneToRender = userViewMain.loadDashboard();
        break;

        default:
            paneToRender = mostRecentView;
        }
    }
}

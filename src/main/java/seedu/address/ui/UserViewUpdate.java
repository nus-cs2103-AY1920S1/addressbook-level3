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
    }

    /**
     * Retrieves user command's preamble and calls UserViewMain to retrieve the child component of the placeholder pane.
     * Clears the current view first and switches to the desired view.
     *
     * @param commandText the placeholder pane in the view
     */
    public void parseUserCommand(String commandText) {
        String preamble = commandText.split(" ")[0];

        paneToRender.getChildren().clear();

        switch(preamble) {
        case "list":
            paneToRender.getChildren().add(userViewMain.loadTasks());
            break;

        default:
            paneToRender.getChildren().add(userViewMain.loadDashboard());
        }
    }
}

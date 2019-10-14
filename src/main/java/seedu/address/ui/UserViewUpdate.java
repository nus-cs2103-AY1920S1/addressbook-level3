package seedu.address.ui;

import javafx.scene.layout.Pane;
import seedu.address.logic.parser.ParserUtil;

/**
 * Communicates with the UserViewManager to update the view based on user commands.
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
     * Parses command and calls UserViewMain to retrieve the child component of the placeholder pane
     * Clears the current view, before setting the new one.
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

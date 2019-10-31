package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for a reward window
 */
public class RewardWindow extends UiPart<Stage> {

    public static final String REWARD_MESSAGE = "You have completed new tasks. Good job! ";

    private static final Logger logger = LogsCenter.getLogger(RewardWindow.class);
    private static final String FXML = "RewardWindow.fxml";

    @FXML
    private Label rewardMessage;

    /**
     * Creates a new RewardWindow.
     *
     * @param root Stage to use as the root of the RewardWindow.
     */
    public RewardWindow(Stage root) {
        super(FXML, root);
        rewardMessage.setText(REWARD_MESSAGE);
    }

    /**
     * Creates a new RewardWindow.
     */
    public RewardWindow() {
        this(new Stage());
    }

    public void show() {
        logger.fine("Showing reward page.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the reward window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

}

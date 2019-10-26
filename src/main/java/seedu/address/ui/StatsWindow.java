package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

public class StatsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);
    private static final String FXML = "StatsWindow.fxml";

    /**
     * Creates a new StatsWindow.
     * @param root Stage to use as the root of the HelpWindow.
     */
    public StatsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new StatsWindow.
     */
    public StatsWindow() {
        this(new Stage());
    }


    /**
     * Shows the stats window.
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the stats window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the stats window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the stats window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}

package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * New Window to display the detailed statistics.
 */
public class StatisticsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(FetchEventWindow.class);
    private static final String FXML = "StatisticsWindow.fxml";

    @FXML
    private Label detailLabel;


    /**
     * Creates a new StatisticsWindow.
     *
     * @param root Stage to use as the root of the StatisticsWindow.
     */
    public StatisticsWindow(Stage root, Logic logic) {
        super(FXML, root);
    }

    /**
     * Creates a new StatisticsWindow.
     */
    public StatisticsWindow(Logic logic) {
        this(new Stage(), logic);
    }


    /**
     * Shows the StatisticsWindow.
     */
    public void show() {
        logger.fine("Showing generated summary of statistics.");
        getRoot().show();
        getRoot().centerOnScreen();

    }

    /**
     * Returns true if the StatisticsWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the StatisticsWindow.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the StatisticsWindow.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Sets the statistics as the the text for the label.
     * @param text
     */
    public void setLabelText(String text) {
        detailLabel.setText(text);
    }
}

package io.xpire.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Allows the result to pop up.
 */
public class ResultWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "ResultWindow.fxml";

    @FXML
    private Label resultText;

    /**
     * Creates a new ResultWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     *
     */
    public ResultWindow(Stage root) {
        super(FXML, root);
        this.getRoot().initStyle(StageStyle.TRANSPARENT);
    }

    /**
     * Creates a new ResultWindow.
     */
    public ResultWindow() {
        this(new Stage());
    }

    /**
     * Shows the result window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing feedback to user.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the result window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the result window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Hides the result window.
     */
    public void hideOnKey(KeyEvent e) {
        focus();
        hide();
    }

    /**
     * Focuses on the result window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultText.setText(feedbackToUser);
    }
}

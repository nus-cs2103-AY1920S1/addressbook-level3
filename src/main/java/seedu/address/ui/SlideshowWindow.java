package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class SlideshowWindow extends UiPart<Stage> {

    public static final String TIP = "Press 'Esc' to quit slideshow";

    private static final Logger logger = LogsCenter.getLogger(SlideshowWindow.class);
    private static final String FXML = "SlideshowWindow.fxml";


    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;
    @FXML
    private Label tipLabel;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public SlideshowWindow(Stage root) {
        super(FXML, root);

        tipLabel.setText(TIP);
        root.setFullScreen(true);

        // Set keyboard listener
        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                root.close();
            }
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public SlideshowWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Show slideshow.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the slideshow window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the slideshow window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

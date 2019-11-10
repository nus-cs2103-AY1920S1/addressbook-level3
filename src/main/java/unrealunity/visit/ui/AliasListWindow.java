package unrealunity.visit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Window containing the list of existing user-defined aliases.
 */
public class AliasListWindow extends UiPart<Stage> {
    private static final String FXML = "AliasListWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(AliasListWindow.class);

    @FXML
    private TextArea aliasesGoesHere;

    /**
     * Creates a new AliasListWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public AliasListWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new AliasListWindow.
     */
    public AliasListWindow() {
        this(new Stage());
    }

    public void setup(String aliases) {
        aliasesGoesHere.setText(aliases);
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing list of aliases.");
        getRoot().show();
        getRoot().centerOnScreen();
        getRoot().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    getRoot().hide();
                }
            }
        );
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

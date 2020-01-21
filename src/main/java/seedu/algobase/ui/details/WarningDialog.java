package seedu.algobase.ui.details;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.ui.UiPart;

/**
 * Controller for the warning dialog.
 */
public class WarningDialog extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(WarningDialog.class);
    private static final String FXML = "WarningDialog.fxml";
    private final Consumer<Object[]> callback;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private CheckBox checkbox;
    @FXML
    private Label warningMessage;

    /**
     * Creates a new Warning Dialog
     *
     * @param root Stage to use as the root of the WarningDialog
     */
    public WarningDialog(Stage root, String message, Consumer<Object[]> callback) {
        super(FXML, root);
        warningMessage.setText(message);
        checkbox.setVisible(false);
        this.callback = callback;
    }

    /**
     * Creates a new Warning Dialog with a checkbox.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public WarningDialog(Stage root, String message, String checkboxMessage, Consumer<Object[]> callback) {
        super(FXML, root);
        warningMessage.setText(message);
        checkbox.setText(checkboxMessage);
        this.callback = callback;
    }

    /**
     * Creates a new WarningDialog.
     */
    public WarningDialog(String message, Consumer<Object[]> callback) {
        this(new Stage(), message, callback);
    }


    /**
     * Creates a new WarningDialog with a checkbox.
     */
    public WarningDialog(String message, String checkboxMessage, Consumer<Object[]> callback) {
        this(new Stage(), message, checkboxMessage, callback);
    }

    /**
     * Shows the warning dialog..
     */
    void show() {
        logger.fine("Opening a Warning Dialog to confirm delete");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the warning dialog is currently being shown.
     */
    boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the warning Dialog.
     */
    void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the warning Dialog.
     */
    void focus() {
        getRoot().requestFocus();
    }

    /**
     * Confirms the delete and triggers the callback function.
     */
    @FXML
    private void confirm() {
        logger.info("Clicked on the confirm button in the warning dialog");
        callback.accept(new Object[] { true, checkbox.isSelected() });
    }

    /**
     * Cancels the delete and triggers the callback function.
     */
    @FXML
    private void cancel() {
        logger.info("Clicked on the cancel button in the warning dialog");
        callback.accept(new Object[] { false, false });
    }
}

//@@author CarbonGrid
package seedu.address.ui;

import javafx.scene.input.KeyCode;

/**
 * MainWindow's Interface of CommandBox.
 */
public interface CommandBoxInterface {

    /**
     * CommandBox TextChanged Callback.
     */
    void handleCommandBoxTextChanged(String commandText);

    /**
     * CommandBox KeyPressed Callback.
     */
    void handleCommandBoxKeyPressed(KeyCode keycode);
}

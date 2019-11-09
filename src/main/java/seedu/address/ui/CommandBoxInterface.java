//@@author CarbonGrid
package seedu.address.ui;

import javafx.scene.input.KeyCode;

/**
 * Manages AutoComplete of MainWindow.
 */
public interface CommandBoxInterface {

    /**
     * Updates AutoComplete with commandText.
     */
    void handleCommandBoxTextChanged(String commandText);

    /**
     * Called whenever keyPressed on AutoComplete.
     */
    void handleCommandBoxKeyPressed(KeyCode keycode);
}

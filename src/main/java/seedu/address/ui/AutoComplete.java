package seedu.address.ui;

import javafx.scene.input.KeyCode;

/**
 * Manages AutoComplete of MainWindow.
 */
public interface AutoComplete {

    /**
     * Updates AutoComplete with commandText.
     */
    void updateCommandAutoComplete(String commandText);

    /**
     * Called whenever keyPressed on AutoComplete.
     */
    void updateSelectionKeyPressedCommandBox(KeyCode keycode);

}

package seedu.address.ui;

import javafx.scene.input.KeyCode;

public interface AutoComplete {

    /**
     * Updates AutoComplete with commandText.
     */
    void updateCommandAutoComplete(String commandText);

    /**
     * Called whenever keyPressed on AutoComplete.
     */
    void onKeyPressed(KeyCode keycode);

}

//@@author francislow

package cs.f10.t1.nursetraverse.ui;

import javafx.scene.input.KeyCode;

/**
 * An observer interface to observe the command box class for userinputs
 * This interface was created so as to provide a facade as ui interacts with one another
 */
public interface UiObserver {
    /**
     * Updates observer after key press activity
     * @param keyCode key code that was pressed by user
     */
    void update(KeyCode keyCode);

    /**
     * Updates display of observer with a new string after key press activity
     *
     * @param keyCode key code that was pressed by user
     * @param newString new string to replace old string in observer
     */
    void update(KeyCode keyCode, String newString);
}

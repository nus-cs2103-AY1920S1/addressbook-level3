package cs.f10.t1.nursetraverse.ui;

import javafx.scene.input.KeyCode;

/**
 * An observer interface to observe the command box class for userinputs
 */
public interface Observer {
    void update(KeyCode keyCode);

    void update(KeyCode keyCode, String resultString);
}

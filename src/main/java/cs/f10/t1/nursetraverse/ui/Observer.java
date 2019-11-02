package cs.f10.t1.nursetraverse.ui;

import javafx.scene.input.KeyCode;

public interface Observer {
    void update(KeyCode keyCode);

    void update(KeyCode keyCode, String resultString);
}

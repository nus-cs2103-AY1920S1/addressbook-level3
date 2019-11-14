package organice.ui;

import javafx.scene.control.Label;
import organice.model.person.Type;

/**
 * API of UI component
 */
public interface Form {

    /** Starts the UI (and the App).  */
    Label getName();
    Label getNric();
    Label getPhone();

    void setName(String name);
    void setNric(String nric);
    void setPhone(String phone);
    void increaseProgress();
    void decreaseProgress();

    Type getType();
}

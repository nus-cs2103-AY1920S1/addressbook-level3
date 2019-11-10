package budgetbuddy.ui;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.concurrent.Semaphore;

import budgetbuddy.logic.script.ScriptBindingInterfaces;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.logic.script.ScriptEnvironmentInitialiser;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Provides convenience classes to the script environment.
 * <p>
 * This class breaks a lot of abstraction barriers in order for the scripting experience to be ergonomic.
 * The abstraction-breaking is contained within this class.
 */
public class ScriptUiBinding implements ScriptEnvironmentInitialiser {
    @Override
    public void initialise(ScriptEngine engine) {
        engine.setVariable("showAlert", (ScriptBindingInterfaces.StringString) this::scriptShowAlert);
    }

    /**
     * Provides <code>showAlert(title, message)</code>.
     */
    private Object scriptShowAlert(String title, String message) {
        requireAllNonNull(title, message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Script alert");
        alert.setHeaderText(title);
        alert.setContentText(message);

        if (Platform.isFxApplicationThread()) {
            alert.showAndWait();
        } else {
            // there is currently no situation in Budget Buddy where scripts can be called from
            // not-the-UI-thread, but let's be safe
            Semaphore sem = new Semaphore(0);
            Platform.runLater(() -> {
                alert.showAndWait();
                sem.release();
            });
            try {
                sem.wait();
            } catch (InterruptedException ex) {
                // too bad!
            }
        }

        return null;
    }
}

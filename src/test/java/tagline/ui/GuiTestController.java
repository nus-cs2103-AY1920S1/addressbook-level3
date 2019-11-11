//@@author tanlk99
package tagline.ui;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tagline.logic.Logic;

/**
 * A singleton class that controls the execution of GUI tests.
 */
public class GuiTestController {
    private static GuiTestController guiTestControllerInstance = new GuiTestController();
    private boolean isStageInitialized = false;

    private GuiTestController() {}

    public static GuiTestController getInstance() {
        return guiTestControllerInstance;
    }

    /**
     * Sets up the stage style. Can only be done once per testing session.
     */
    public void initStageStyle(Stage stage) {
        if (!isStageInitialized) {
            stage.initStyle(StageStyle.DECORATED);
            isStageInitialized = true;
        }
    }

    /**
     * Sets up the stage as a main window.
     */
    public void initMainWindow(Stage stage, Logic logic) throws TimeoutException {
        FxToolkit.setupStage(s -> {
            MainWindow mainWindow = new MainWindow(s, logic);
            mainWindow.show();
            mainWindow.fillInnerParts();
        });
        FxToolkit.showStage();
    }

    /**
     * Sets up the stage with a scene root.
     */
    public void initSceneRoot(Parent root) throws TimeoutException {
        FxToolkit.setupSceneRoot(() -> root);
        FxToolkit.showStage();
    }
    /**
     * Pauses for human-visible results after a test in non-headless mode.
     */
    public void pause(FxRobot robot) {
        String headlessPropertyValue = System.getProperty("testfx.headless");
        if (headlessPropertyValue != null && headlessPropertyValue.equals("true")) {
            return;
        }

        robot.sleep(500);
    }

    /**
     * Performs teardown after a test class is completed.
     */
    public void doTearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

}


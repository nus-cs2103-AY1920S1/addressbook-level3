package dream.fcard.gui;

import dream.fcard.model.State;
import java.util.logging.Logger;

import dream.fcard.MainApp;
import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.core.commons.util.StringUtil;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The manager of the UI component.
 */
public class UiManager {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private MainWindow mainWindow;
    private State state;

    public UiManager(State state) {
        this.state = state;
    }

    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        // set the application icon
        primaryStage.getIcons().add(getImage(GuiSettings.getApplicationIcon()));

        try {
            mainWindow = new MainWindow(primaryStage, state);
            // todo: might make sense to call mainWindow's methods rather than putting everything in its constructor
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            //showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }
}

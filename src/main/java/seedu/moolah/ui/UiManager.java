package seedu.moolah.ui;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.moolah.MainApp;
import seedu.moolah.commons.core.LogsCenter;
import seedu.moolah.commons.util.StringUtil;
import seedu.moolah.logic.Logic;
import seedu.moolah.logic.Timekeeper;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/moolah.png";

    private Logic logic;
    private Timekeeper timekeeper;
    private Timer eventsTimer;
    private Timer systemTimer;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Timekeeper timekeeper) {
        super();
        this.logic = logic;
        this.timekeeper = timekeeper;
        this.systemTimer = new Timer();
        this.eventsTimer = new Timer();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, logic, timekeeper, systemTimer, eventsTimer);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

            mainWindow.displayReminders();

            long updateTimeInterval = 50;

            systemTimer.schedule(new TimerTask() {
                public void run() {
                    timekeeper.updateTime();
                }
            }, 0, updateTimeInterval);

            long checkEventsInterval = 2000;

            eventsTimer.schedule(new TimerTask() {
                public void run () {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mainWindow.handleTranspiredEvents();
                        }
                    });
                }
            }, 0, checkEventsInterval);

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}

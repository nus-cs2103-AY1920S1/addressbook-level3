package seedu.address.ui;

import java.io.InputStream;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;

/**
 * The manager of the UI component.\
 *
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";
    public static final String ALERT_SOUND_PATH = "/alert.wav";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/notebook-icon.png";

    private Logic logic;
    private MainWindow mainWindow;
    private Text text = new Text();

    public UiManager(Logic logic) {
        super();
        this.logic = logic;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
            createReminder(10000, "U r weird", "but u r cool");
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
     * function to make reminders
     * called by scheduler commands
     * @param duration
     * @param reminderType
     * @param reminderDetails
     */
    private void createReminder(int duration, String reminderType, String reminderDetails) {
        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(duration),
            ae -> countDownAlert(reminderType, reminderDetails)));
        timeline.play();
    }
    /**
     * alert for scheduler.
     * sets properties of alert then
     * plays sound file and shows alert dialog
     */
    private void countDownAlert(String reminderType, String reminderDetails) {

        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(mainWindow.getPrimaryStage());
        alert.setTitle("Reminder!");
        alert.setHeaderText(reminderType);
        alert.setContentText(reminderDetails);
        playSound();
        alert.show();

    }
    /**
     * handles playing alert audio for scheduled alert.
     * get .wav file from resource folder as input stream,
     * then open and play.
     */
    private void playSound() {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(ALERT_SOUND_PATH);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            Clip sound = AudioSystem.getClip();
            sound.open(audioStream);
            sound.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

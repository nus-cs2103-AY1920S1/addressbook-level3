package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.task.Task;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    private static boolean loggedInSuccessful = false;

    private static MainWindow mainWindow;
    private Stage pStage;
    private Logic logic;

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

            //mainWindow.fillStudents();
            mainWindow.fillTasks();
            mainWindow.hide();
            mainWindow.showLogin();
            //mainWindow.show();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public void isAbleToLoginSuccessfully() {
        loggedInSuccessful = true;
    }

    /**
     * Starts the reminderWindow the first time opening Tutoraid.
     */
    public static void startReminderWindow() {
        logger.info("Changing to Student Profile...");

        try {
            mainWindow.handleReminderBox();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to earnings tab.
     */
    public static void startStudentProfile() {
        logger.info("Changing to Student Profile...");

        try {
            if (mainWindow != null) {
                mainWindow.show(); //This should be called before creating other UI parts
                mainWindow.fillStudents();
            }
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to earnings tab.
     */
    public static void startEarnings() {
        logger.info("Changing to Earning...");

        try {
            if (mainWindow != null) {
                mainWindow.show(); //This should be called before creating other UI parts
                mainWindow.fillEarnings();
            }
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to tasks tab.
     */
    public static void startTasks() {
        logger.info("Changing to Tasks...");

        try {
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillTasks();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to reminder tab.
     */
    public static void startReminders() {
        logger.info("Changing to Reminders...");

        try {
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillReminders();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to notepad tab/
     */
    public static void startNotes() {
        logger.info("Changing to Notes...");

        try {
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillNotes();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to calendar tab.
     */
    public static void startCalendar() {
        logger.info("Changing to Calendar...");

        try {
            mainWindow.show();
            mainWindow.fillCalendar();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to login window.
     */
    public static void startLoginWindow() {
        logger.info("Changing to Login...");

        try {
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.hide();
            mainWindow.showLogin();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    /**
     * To change tab to login window.
     */
    public static void deleteNotesButton(int index) {
        logger.info("Deleting Note...");

        try {
            mainWindow.deleteNoteButton(index);
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during deletion", e);
        }
    }

    /**
     * To change tab to tasks tab.
     */
    public static void startTaskWindow(LocalDate date) {
        logger.info("Changing to Tasks Tab by Date...");

        try {
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillTasks();
            mainWindow.findTaskByDate(date);
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public static ObservableList<Task> returnTaskByDate(LocalDate date) {
        logger.info("Changing to Calendar showing Task by Date...");

        try {
            return mainWindow.returnTaskByDate(date);
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
            return null;
        }
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    static void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        Stage stage = mainWindow.getPrimaryStage();
        showAlertDialogAndWait(stage, type, title, headerText, contentText);
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
    private static void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }
}

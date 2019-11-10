package com.dukeacademy.ui;

import java.util.logging.Logger;

import com.dukeacademy.MainApp;
import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.util.StringUtil;

import com.dukeacademy.logic.commands.CommandLogic;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.state.ApplicationState;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    /**
     * The constant ALERT_DIALOG_PANE_FIELD_ID.
     */
    private static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/dukeacademy-icon.png";

    private final CommandLogic commandLogic;
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final NotesLogic notesLogic;
    private final ApplicationState applicationState;
    private MainWindow mainWindow;

    /**
     * Instantiates a new Ui manager.
     *
     * @param commandLogic           the command logic
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     */
    public UiManager(CommandLogic commandLogic, QuestionsLogic questionsLogic,
                     ProgramSubmissionLogic programSubmissionLogic, NotesLogic notesLogic,
                     ApplicationState applicationState) {
        super();
        this.commandLogic = commandLogic;
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
        this.notesLogic = notesLogic;
        this.applicationState = applicationState;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage());

        try {
            mainWindow = new MainWindow(primaryStage, commandLogic, questionsLogic,
                    programSubmissionLogic, notesLogic, applicationState);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown(e);
        }
    }

    private Image getImage() {
        return new Image(MainApp.class.getResourceAsStream(
            UiManager.ICON_APPLICATION));
    }

    /**
     * Show alert dialog and wait.
     *  @param title       the title
     * @param headerText  the header text
     * @param contentText the content text
     */
    private void showAlertDialogAndWait(String title, String headerText,
                                        String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), AlertType.ERROR, title, headerText, contentText);
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
    private void showFatalErrorDialogAndShutdown(Throwable e) {
        logger.severe("Fatal error during initializing"
            + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait("Fatal error during initializing", e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}

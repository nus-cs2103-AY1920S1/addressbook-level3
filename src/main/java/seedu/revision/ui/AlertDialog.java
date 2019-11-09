package seedu.revision.ui;

import static seedu.revision.ui.UiManager.ALERT_DIALOG_PANE_FIELD_ID;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/** AlertDialog class that is used to prompt users for inputs.**/
public class AlertDialog {

    public static final String NEXT_LEVEL_TITLE = "Well done!";
    public static final String END_QUIZ_TITLE = "End of quiz!";

    private Alert alert;
    private ButtonType yesButton;
    private ButtonType noButton;

    /**
     * Private initializer to prevent external parties from accessing the constructor. Initialises with default
     * stylesheet and buttons. Defensive programming.
     */
    private AlertDialog() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add("view/OrangeTheme.css");
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, noButton);
    }

    /**
     * Initialises an alert that will be shown when the user tries to restore the app.
     * @return AlertDialog with warning to restore the app.
     */
    public static AlertDialog getRestoreAlert() {
        AlertDialog restoreAlert = new AlertDialog();
        restoreAlert.alert.setAlertType(Alert.AlertType.WARNING);
        restoreAlert.alert.setTitle("Warning!");
        restoreAlert.alert.setHeaderText(null);
        restoreAlert.alert.setContentText("Are you sure? This cannot be undone.");

        return restoreAlert;
    }
    /**
     * Initialises an alert that will be shown when the user completes a level in the quiz.
     * @param nextLevel the next level in the quiz.
     * @param score the user's current score so far.
     * @param totalScore the total score of the user
     * @return AlertDialog with the next level details set.
     */

    public static AlertDialog getNextLevelAlert(int nextLevel, int score, int totalScore) {
        AlertDialog nextLevelAlert = new AlertDialog();
        nextLevelAlert.alert.setTitle(NEXT_LEVEL_TITLE);
        nextLevelAlert.alert.setHeaderText(null);
        nextLevelAlert.alert.setContentText("You have completed level " + (nextLevel - 1) + "\n"
                + "Your current score is: " + score + "/" + totalScore + "\n"
                + "Would you like to proceed to level " + nextLevel + "?\n"
                + "Press [ENTER] to proceed.\n"
                + "Press [ESC] to return to main screen.");

        return nextLevelAlert;
    }

    /**
     * Initialises an alert that will be shown when the user ends the quiz.
     * @param score the user's current score so far.
     * @param totalScore the total score of the user
     * @return
     */
    public static AlertDialog getEndAlert(int score, int totalScore, boolean isFailure) {
        AlertDialog endAlert = new AlertDialog();
        endAlert.alert.setTitle(END_QUIZ_TITLE);
        endAlert.alert.setHeaderText(null);

        if (isFailure) {
            endAlert.alert.setAlertType(Alert.AlertType.ERROR);
            endAlert.alert.setContentText("Better luck next time! :P Your score is " + score
                    + "/" + totalScore + "\n"
                    + "Try again?\n"
                    + "Press [ENTER] to try again.\n"
                    + "Press [ESC] to return to main screen.");
        } else {
            endAlert.alert.setAlertType(Alert.AlertType.INFORMATION);
            endAlert.alert.setContentText("Quiz has ended! Your final score is " + score
                    + "/" + totalScore + "\n"
                    + "Try again?\n"
                    + "Press [ENTER] to try again.\n"
                    + "Press [ESC] to return to main screen.");
        }

        return endAlert;
    }

    /**
     * Shows the alert dialog and waits for user input which will be returned as a result.
     * @return the ButtonType that the user chose if any.
     */
    public Optional<ButtonType> showAndWait() {
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    public ButtonType getYesButton() {
        return yesButton;
    }

    public ButtonType getNoButton() {
        return noButton;
    }

    public Alert getAlert() {
        return alert;
    }
}

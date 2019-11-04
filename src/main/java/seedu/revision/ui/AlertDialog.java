package seedu.revision.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static seedu.revision.ui.UiManager.ALERT_DIALOG_PANE_FIELD_ID;

/** AlertDialog class that is used to prompt users for inputs.**/
public class AlertDialog {

    private Alert alert;
    private ButtonType tryAgainButton;
    private ButtonType endButton;

    /**
     * Private initializer to prevent external parties from accessing the constructor. Initialises with default
     * stylesheet and buttons.
     */
    private AlertDialog() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        tryAgainButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        endButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(tryAgainButton, endButton);
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
        nextLevelAlert.alert.setTitle("Well done!");
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
        endAlert.alert.setTitle("End of Quiz!");
        endAlert.alert.setHeaderText(null);

        if (isFailure) {
            endAlert.alert.setAlertType(Alert.AlertType.ERROR);
            endAlert.alert.setContentText("Better luck next time! :P Your score is " + score
                    + "/" + totalScore + "\n"
                    + "Try again?\n"
                    + "Press [ENTER] to try again.\n"
                    + "Press [ESC] to return to main screen.");
        } else {
            endAlert.alert.setContentText("Quiz has ended! Your final score is " + score
                    + "/" + totalScore + "\n"
                    + "Try again?\n"
                    + "Press [ENTER] to try again.\n"
                    + "Press [ESC] to return to main screen.");
        }

        return endAlert;
    }

    public Optional<ButtonType> showAndWait() {
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    public ButtonType getEndButton() {
        return endButton;
    }
}

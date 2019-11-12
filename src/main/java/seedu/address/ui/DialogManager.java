package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The manager of dialogs to display information and get user input outside MainWindow.
 */
public class DialogManager {
    /**
     * Shows a dialog which allows the user to set a new master password.
     * @return the user's response.
     */
    public static Optional<String> showCreatePasswordDialog(boolean isPasswordInvalid) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("SecureIT");
        dialog.setHeaderText("Create your master password");
        VBox content = new VBox();
        Label warningLabel = new Label();
        warningLabel.setText(
                "The master password and all data stored by SecureIT\n"
                + "are NOT recoverable if you lose the master password.\n"
                + "Please remember it carefully.\n "
        );
        Label label1 = new Label();
        label1.setText("Password: ");
        PasswordField passwordField = new PasswordField();
        Label label2 = new Label();
        label2.setText("Confirm Password: ");
        PasswordField confirmPasswordField = new PasswordField();
        Label wrongLabel = new Label();
        wrongLabel.setText(
                "The password is empty or the two passwords do not\n"
                + "match. Please try again.");
        wrongLabel.setTextFill(Color.RED);
        if (isPasswordInvalid) {
            content.getChildren()
                    .addAll(warningLabel, label1, passwordField, label2, confirmPasswordField, wrongLabel);
        } else {
            content.getChildren()
                    .addAll(warningLabel, label1, passwordField, label2, confirmPasswordField);
        }
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    return confirmPasswordField.getText();
                } else {
                    return "";
                }
            }
            return null;
        });
        return dialog.showAndWait();
    }

    /**
     * Shows a dialog which requires the user to input the master password for validation.
     * @return the user's response.
     */
    public static Optional<String> showValidatePasswordDialog(boolean isPasswordIncorrect) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("SecureIT");
        dialog.setHeaderText("Enter your master password");
        VBox content = new VBox();
        Label label = new Label();
        label.setText("Password: ");
        Label wrongLabel = new Label();
        wrongLabel.setText("Wrong password. Please try again.");
        wrongLabel.setTextFill(Color.RED);
        PasswordField passwordField = new PasswordField();
        if (isPasswordIncorrect) {
            content.getChildren().addAll(label, passwordField, wrongLabel);
        } else {
            content.getChildren().addAll(label, passwordField);
        }
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return passwordField.getText();
            }
            return null;
        });
        return dialog.showAndWait();
    }
}

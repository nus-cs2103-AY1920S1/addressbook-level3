package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;

/**
 * The manager of dialogs to display information and get user input outside MainWindow.
 */
public class DialogManager {
    /**
     * Shows a dialog which allows the user to set a new master password.
     * @return the user's response.
     */
    public static Optional<String> showCreatePasswordDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("SecureIT");
        dialog.setHeaderText("Create your master password");
        dialog.setContentText("Password: ");
        return dialog.showAndWait();
    }

    /**
     * Shows a dialog which requires the user to input the master password for validation.
     * @return the user's response.
     */
    public static Optional<String> showValidatePasswordDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("SecureIT");
        dialog.setHeaderText("Enter your master password");
        dialog.setContentText("Password: ");
        return dialog.showAndWait();
    }
}

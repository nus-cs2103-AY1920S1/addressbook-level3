package seedu.elisa.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;



/**
 * This class is the Ui for UserDialogBox
 */
class ElisaReminderBox extends HBox {

    @FXML
    private Label dialog;

    private ElisaReminderBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ElisaReminderBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text); }

    static ElisaReminderBox getElisaDialog(String text) {
        return new ElisaReminderBox(text);
    }
}

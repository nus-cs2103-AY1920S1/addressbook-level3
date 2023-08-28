package seedu.address.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

//@@author arjavibahety-reused
//Reused from @bjhoohaha/duke's AlertWindow.java
/**
 * The Notification Window. Provides the basic layout containing
 * a notification message.
 */
public class NotifWindow {
    private Alert notif;

    public NotifWindow() {
        this.notif = new Alert(Alert.AlertType.INFORMATION);
        setStyling();
    }

    public NotifWindow(Alert.AlertType notifType) {
        this.notif = new Alert(notifType);
        setStyling();
    }

    public void setTitle(String title) {
        notif.setTitle(title);
    }

    public void setContent(String message) {
        notif.setHeaderText(message);
    }

    public void display() {
        notif.showAndWait();
    }

    //@@author shaoyi1997
    private void setStyling() {
        DialogPane dialogPane = notif.getDialogPane();
        dialogPane.getStylesheets().add("view/DarkTheme.css");
        dialogPane.getStyleClass().add("dialog-pane");
        notif.initStyle(StageStyle.TRANSPARENT);
    }
    //@@author
}
//@@author

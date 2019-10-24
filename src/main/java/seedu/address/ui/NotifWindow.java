package seedu.address.ui;

import javafx.scene.control.Alert;

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
    }

    public NotifWindow(Alert.AlertType notifType) {
        this.notif = new Alert(notifType);
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
}
//@@author

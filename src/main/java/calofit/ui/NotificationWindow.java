package calofit.ui;

import java.util.logging.Logger;

import calofit.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotificationWindow extends UiPart<Stage>{

    private static final Logger logger = LogsCenter.getLogger(NotificationWindow.class);
    private static final String FXML = "NotificationWindow.fxml";

    @FXML
    private Label notificationMessage;

    private NotificationWindow(Stage root, String message) {
        super(FXML, root);
        notificationMessage.setText(message);
    }

    public NotificationWindow(String message) {
        this(new Stage(), message);
    }

    public void show() {
        logger.fine("Showing notification.");
        getRoot().show();
        getRoot().centerOnScreen();
    }
}

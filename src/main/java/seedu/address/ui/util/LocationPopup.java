package seedu.address.ui.util;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A class to show pop ups to indicate location suggestions.
 */
public class LocationPopup {
    private Node popupDetails;
    private Stage popupStage;

    public LocationPopup(Node details) {
        this.popupDetails = details;
        this.popupStage = new Stage();
        ScrollPane popupContainer = new ScrollPane();
        popupContainer.setContent(details);
        Scene layout = new Scene(popupContainer);
        popupContainer.setStyle("-fx-background: #222831;");
        layout.getStylesheets().add("/view/DarkTheme.css");
        layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    popupStage.close();
                }
            }
        });
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(layout);
    }

    public void show() {
        popupStage.show();
    }

    public void hide() {
        popupStage.close();
    }
}

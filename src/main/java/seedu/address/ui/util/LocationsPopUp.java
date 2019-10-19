package seedu.address.ui.util;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A class to show pop ups to indicate location suggestions.
 */
public class LocationsPopUp {
    private ImageView imageView;
    private GridPane popUpContainer;
    private VBox popUpDetails;
    private Stage popupStage;

    public LocationsPopUp(ImageView imageView, List<String> details) {
        this.imageView = imageView;
        this.popUpDetails = new VBox();
        this.popupStage = new Stage();
        for (String s : details) {
            popUpDetails.getChildren().add(new Label(s));
        }
        popUpContainer = new GridPane();
        //popUpContainer.add(imageView, 0,0);
        popUpContainer.add(popUpDetails, 1, 0);
        Scene layout = new Scene(popUpContainer);
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

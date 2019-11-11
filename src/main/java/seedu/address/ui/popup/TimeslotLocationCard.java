package seedu.address.ui.popup;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

/**
 * UI component to show a timeslot location card.
 * Will not show anything if location not available.
 */
public class TimeslotLocationCard extends UiPart<Region> {
    private static final String FXML = "TimeslotLocationCard.fxml";

    @FXML
    private VBox timeslotLocationCardContainer;

    @FXML
    private ImageView timeslotLocationMap;

    public TimeslotLocationCard(Image image) {
        super(FXML);

        if (image != null) {
            timeslotLocationMap.setImage(image);
            timeslotLocationMap.setFitHeight(400.0);
            timeslotLocationMap.setFitWidth(400.0);
        }
    }

}

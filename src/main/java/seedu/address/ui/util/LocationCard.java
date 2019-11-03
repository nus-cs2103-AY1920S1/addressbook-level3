package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

/**
 * UI component to show a location card. Used to show the top 3 locations in LocationView objects.
 */
public class LocationCard extends UiPart<Region> {
    private static final String FXML = "LocationCard.fxml";

    @FXML
    private VBox locationCardContainer;
    @FXML
    private Label locationTitle;
    @FXML
    private Label locationNameLabel;
    @FXML
    private Label locationAvgTravelDistanceLabel;
    @FXML
    private ImageView locationMap;

    public LocationCard(String title, Image image, String locationName, String locationDistance) {
        super(FXML);
        locationTitle.setText(title);
        locationNameLabel.setText(locationName);
        locationAvgTravelDistanceLabel.setText(locationDistance);
        locationMap.setImage(image);
        locationMap.setFitHeight(400.0);
        locationMap.setFitWidth(400.0);
    }
}

package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

/**
 * A class to generate the locations view display.
 */
public class LocationsView extends UiPart<Region> {
    private static String FXML = "LocationsView.fxml";

    private static String firstLocation = "First choice: ";
    private static String secondLocation = "Second choice: ";
    private static String thirdLocation = "Third choice: ";

    @FXML
    private StackPane locationMapPlaceholder;
    @FXML
    private VBox textContainer;
    @FXML
    private StackPane locationsViewContainer;

    public LocationsView() {
        super(FXML);
        Image placeHolder = new Image(getClass().getResourceAsStream("/images/timebook_logo.png"));
        ImageView imageView = new ImageView(placeHolder);
        imageView.setFitWidth(400.0);
        imageView.setFitHeight(400.0);
        locationMapPlaceholder.getChildren().add(imageView);
        textContainer.getChildren().addAll(new Label(firstLocation), new Label(secondLocation), new Label(thirdLocation));
    }

}

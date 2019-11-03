package seedu.address.ui.util;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

/**
 * UI component to show the location overview of a particular popup.
 */
public class LocationOverview extends UiPart<Region> {
    private static final String FXML = "LocationOverview.fxml";
    private static final String SOURCE = "Everyone's locations: ";
    private static final String VALID = "Out of all these locations, the valid locations are: ";
    private static final String INVALID = "Locations that cannot be found in NUS: ";
    @FXML
    private VBox locationOverviewContainer;

    @FXML
    private HBox firstChoiceHeader;

    @FXML
    private Label firstChoiceLocation;

    @FXML
    private StackPane sourceLocations;

    @FXML
    private StackPane validLocations;

    @FXML
    private StackPane invalidLocations;

    public LocationOverview(String firstChoice, List<String> locationsEntered,
                            List<String> validLocations, List<String> invalidLocations) {
        super(FXML);
        firstChoiceLocation.setText(firstChoice);
        this.sourceLocations.getChildren().add(getLocationCell(SOURCE, locationsEntered));
        this.validLocations.getChildren().add(
                getLocationCell(VALID, validLocations));
        this.invalidLocations.getChildren().add(
                getLocationCell(INVALID, invalidLocations));
    }

    public HBox getLocationCell(String title, List<String> locationsEntered) {
        HBox locationCell = new HBox();
        HBox locationsEnteredContainer = new HBox();
        for (String s : locationsEntered) {
            Label label = new Label(s);
            locationsEnteredContainer.getChildren().add(label);
        }
        locationsEnteredContainer.setId("locationsEnteredContainer");
        locationCell.setStyle("-fx-alignment: center;");
        locationCell.getChildren().addAll(new Label(title), locationsEnteredContainer);
        return locationCell;
    }
}

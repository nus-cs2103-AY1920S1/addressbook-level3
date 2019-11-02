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

    @FXML
    private VBox locationOverviewContainer;

    @FXML
    private StackPane firstChoiceHeader;

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
        this.sourceLocations.getChildren().add(getLocationCell("Everyone's locations: ", locationsEntered));
        this.validLocations.getChildren().add(
                getLocationCell("Out of all these locations, the valid locations are: ", validLocations));
        this.invalidLocations.getChildren().add(
                getLocationCell("Locations that cannot be found in NUS: ", invalidLocations));
    }

    public HBox getLocationCell(String title, List<String> locationsEntered) {
        HBox locationCell = new HBox();
        VBox locationsEnteredContainer = new VBox();
        for (String s : locationsEntered) {
            locationsEnteredContainer.getChildren().add(new Label(s));
        }
        locationCell.getChildren().addAll(new Label(title), locationsEnteredContainer);
        return locationCell;
    }
}

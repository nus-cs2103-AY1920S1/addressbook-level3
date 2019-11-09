package seedu.address.ui.popup;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.locationdata.ClosestCommonLocationData;
import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.ui.UiPart;

/**
 * A class to generate timeslot displays.
 */
public class TimeslotView extends UiPart<Region> {

    private static final String FXML = "TimeslotView.fxml";

    @FXML
    private GridPane timeslotViewContainer;

    @FXML
    private StackPane timeslotOverview;

    @FXML
    private StackPane timeslotLocationCard;


    public TimeslotView(PersonTimeslot data) {

        super(FXML);
        ClosestCommonLocationData locationData = data.getLocationData();

        timeslotViewContainer.setAlignment(Pos.CENTER);

        TimeslotOverview overview = new TimeslotOverview(data);
        timeslotOverview.getChildren().add(overview.getRoot());

        try {
            Image image = SwingFXUtils.toFXImage(locationData.getImageFirst(), null);
            timeslotLocationCard.getChildren().add(
                    new TimeslotLocationCard(image).getRoot());
        } catch (NullPointerException npe) {
            timeslotLocationCard.getChildren().add(
                    new Label("Location is not available"));
        }
    }

}

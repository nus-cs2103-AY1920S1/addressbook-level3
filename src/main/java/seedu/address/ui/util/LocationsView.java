package seedu.address.ui.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.display.detailwindow.ClosestCommonLocationData;
import seedu.address.ui.UiPart;

/**
 * A class to generate the locations view display.
 */
public class LocationsView extends UiPart<Region> {
    private static final String FXML = "LocationsView.fxml";

    @FXML
    private StackPane locationMapPlaceholder;
    @FXML
    private VBox textContainer;
    @FXML
    private Label firstLocationLabel;
    @FXML
    private Label secondLocationLabel;
    @FXML
    private Label thirdLocationLabel;
    @FXML
    private Label firstAvg;
    @FXML
    private Label secondAvg;
    @FXML
    private Label thirdAvg;
    @FXML
    private StackPane locationsViewContainer;

    public LocationsView(ClosestCommonLocationData data) {
        super(FXML);
        Image placeHolder = SwingFXUtils.toFXImage(data.getImage(), null);
        ImageView imageView = new ImageView(placeHolder);
        imageView.setFitWidth(400.0);
        imageView.setFitHeight(400.0);
        locationMapPlaceholder.getChildren().add(imageView);
        firstLocationLabel.setText(data.getFirstClosest());
        firstAvg.setText(data.getFirstAvg() + "");
        secondLocationLabel.setText(data.getSecondClosest());
        secondAvg.setText(data.getSecondAvg() + "");
        thirdLocationLabel.setText(data.getThirdClosest());
        thirdAvg.setText(data.getThirdAvg() + "");
    }

}

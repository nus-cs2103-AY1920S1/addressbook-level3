package seedu.address.ui.popup;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.locationdata.ClosestCommonLocationData;
import seedu.address.ui.UiPart;

/**
 * A class to generate the locations view display.
 */
public class LocationsView extends UiPart<Region> {
    private static final String FXML = "LocationsView.fxml";
    private static final String firstChoiceTitle = "First choice: ";
    private static final String secondChoiceTitle = "Second choice: ";
    private static final String thirdChoiceTitle = "Third choice: ";

    @FXML
    private GridPane locationViewContainer;

    @FXML
    private StackPane locationOverviewCard;

    @FXML
    private StackPane firstLocationCard;

    @FXML
    private StackPane secondLocationCard;

    @FXML
    private StackPane thirdLocationCard;

    //Independent parts inside locations view.
    private Image placeholder1;
    private Image placeholder2;
    private Image placeholder3;
    private String firstClosest;
    private String firstAvg;
    private String secondClosest;
    private String secondAvg;
    private String thirdClosest;
    private String thirdAvg;

    public LocationsView(ClosestCommonLocationData data) {
        super(FXML);
        this.placeholder1 = SwingFXUtils.toFXImage(data.getImageFirst(), null);
        this.placeholder2 = SwingFXUtils.toFXImage(data.getImageSecond(), null);
        this.placeholder3 = SwingFXUtils.toFXImage(data.getImageThird(), null);
        this.firstClosest = data.getFirstClosest();
        this.firstAvg = data.getFirstAvg();
        this.secondClosest = data.getSecondClosest();
        this.secondAvg = data.getSecondAvg();
        this.thirdClosest = data.getThirdClosest();
        this.thirdAvg = data.getThirdAvg();

        LocationOverview overview = new LocationOverview(firstClosest, data.getLocationEntered(),
                data.getValidLocation(), data.getInvalidLocation());

        locationOverviewCard.getChildren().add(overview.getRoot());

        firstLocationCard.getChildren().add(new LocationCard(firstChoiceTitle,
                placeholder1, firstClosest, firstAvg).getRoot());

        secondLocationCard.getChildren().add(new LocationCard(secondChoiceTitle,
                placeholder2, secondClosest, secondAvg).getRoot());

        thirdLocationCard.getChildren().add(new LocationCard(thirdChoiceTitle,
                placeholder3, thirdClosest, thirdAvg).getRoot());
    }

}

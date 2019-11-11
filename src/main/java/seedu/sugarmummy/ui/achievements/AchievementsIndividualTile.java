package seedu.sugarmummy.ui.achievements;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.sugarmummy.ui.UiPart;

/**
 * A ui for an achievement tile of an achievement, consisting of an image and description.
 */
public class AchievementsIndividualTile extends UiPart<Region> {

    private static final String FXML = "AchievementsIndividualTile.fxml";

    @FXML
    private ImageView tileImageView;

    @FXML
    private Label tileLabel;

    public AchievementsIndividualTile(Image img, String description) {
        super(FXML);
        tileImageView.setImage(img);
        tileLabel.setText(description);
        setLabelFont();
    }

    private void setLabelFont() {
        tileLabel.setStyle("-fx-font-size: 11 !important");
    }
}

package seedu.address.ui.cap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class AchievementBadge extends UiPart<Region> {

    private static final String FXML = "AchievementBadge.fxml";

    @FXML
    private ImageView badgeImage;

    @FXML
    private Label achievementTitle;

    public AchievementBadge(Image img, String title) {
        super(FXML);
        badgeImage.setImage(img);
        achievementTitle.setText(title);
    }
}

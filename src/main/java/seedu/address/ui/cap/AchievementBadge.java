package seedu.address.ui.cap;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * An UI component that displays an achievement badge of a {@code student}.
 */
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

    public void setUpdatedRankToUser(Image img, String updatedRankToUser) {
        requireNonNull(updatedRankToUser);
        badgeImage.setImage(img);
        achievementTitle.setText(updatedRankToUser);
    }
}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the title of the achievements window used to display the user's list of
 * achievements.
 */
public class AchievementsTitle extends UiPart<Region> {

    private static final String FXML = "AchievementsTitle.fxml";

    @FXML
    private Label achievementsTitleLabel;

    @FXML
    private Label achievementsTitleDescLabel;

    public AchievementsTitle(String title, String titleDesc) {
        super(FXML);
        achievementsTitleLabel.setText(title);
        achievementsTitleDescLabel.setText(titleDesc);
    }
}

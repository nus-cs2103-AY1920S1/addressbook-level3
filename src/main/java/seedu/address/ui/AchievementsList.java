package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import seedu.address.MainApp;
import seedu.address.model.achievements.Achievement;
import seedu.address.model.record.RecordType;

/**
 * A ui for an achievement list of a specific record type.
 */
public class AchievementsList extends UiPart<Region> {

    private static final String FXML = "AchievementsList.fxml";

    @FXML
    private Label recordTypeLabel;

    @FXML
    private TilePane achievementsTilePane;

    public AchievementsList(RecordType recordType, List<Achievement> achievementsList) {
        super(FXML);

        recordTypeLabel.setText(recordType.toString());

        for (Achievement achievement : achievementsList) {
            Image img = new Image(MainApp.class.getResourceAsStream("/images/achievements/"
                            + achievement.getImageName() + ".png"));
            AchievementsIndividualTile achievementsIndividualTile = new AchievementsIndividualTile(img,
                    achievement.getDisplayMessage());

            achievementsTilePane.getChildren().add(achievementsIndividualTile.getRoot());
        }
    }
}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class AchievementsPane extends UiPart<Region> {

    private static final String FXML = "AchievementsPane.fxml";
    private static String displayImage = "/images/user.png";

    private AchievementsTitle achievementsTitle;
    private Achievements achievements;
    private Profile profile;

    @FXML
    private HBox achievementsTitlePlaceholder;

    @FXML
    private HBox achievementsPlaceholder;

    public AchievementsPane() {
        super(FXML);
        achievementsTitle = new AchievementsTitle("My Achievements",
                "Hi Amy, here are the list of achievements you have collected so far.");
        achievementsTitlePlaceholder.getChildren().add(achievementsTitle.getRoot());

        achievements = new Achievements();
        for (int i = 0; i <= 24; i++) {
            int n = i % 8 + 1;
            Image img = new Image(MainApp.class.getResourceAsStream("/images/sample_achievement_" + n + ".png"));
            ImageView imageView = new AchievementsImageView().getImageView();
            imageView.setImage(img);
            achievements.getTilePane().getChildren().add(imageView);
        }
        achievementsPlaceholder.getChildren().add(achievements.getRoot());
    }
}

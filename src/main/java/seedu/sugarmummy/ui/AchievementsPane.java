package seedu.sugarmummy.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.record.RecordType;

/**
 * An achievements pane consisting of the display components of a user's achievements, including headers.
 */
public class AchievementsPane extends UiPart<Region> {

    private static final String FXML = "AchievementsPane.fxml";
    private static String displayImage = "/images/user.png";

    private AchievementsTitle achievementsTitle;
    private Achievements achievements;
    private Map<RecordType, List<Achievement>> achievementsMap;

    @FXML
    private HBox achievementsTitlePlaceholder;

    @FXML
    private HBox achievementsPlaceholder;

    public AchievementsPane(Map<RecordType, List<Achievement>> achievementsMap) {
        super(FXML);
        this.achievementsMap = new HashMap<>();
        achievementsMap.forEach((recordType, achievementsList) -> {
            List<Achievement> achievementListCopy = new ArrayList<>();
            achievementsList.forEach(achievement -> achievementListCopy.add(achievement.copy()));
            this.achievementsMap.put(recordType, achievementListCopy);
        });

        achievementsTitle = new AchievementsTitle("My Achievements",
                "Hi Amy, here are the list of achievements you have collected so far.");
        achievementsTitlePlaceholder.getChildren().add(achievementsTitle.getRoot());

        achievements = new Achievements(achievementsMap);
        achievementsPlaceholder.getChildren().add(achievements.getRoot());
    }

    public Map<RecordType, List<Achievement>> getAchievementsMap() {
        return achievementsMap;
    }
}

package seedu.address.ui;

import javafx.scene.layout.HBox;

public class AchievementsCache {

    private Achievements achievements;

    AchievementsCache(Achievements achievements) {
        this.achievements = achievements;
    }

    public Achievements getAchievements() {
        return achievements;
    }

    public boolean isEmpty() {
        return achievements == null;
    }
}

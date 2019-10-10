package seedu.address.ui;

/**
 * A cache for storing Achievements so once loaded future loading of AchievementsWindow will be much faster.
 */
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

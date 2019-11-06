package seedu.sugarmummy.model.achievements;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.model.achievements.AchievementState.ACHIEVED;
import static seedu.sugarmummy.model.achievements.AchievementState.YET_TO_ACHIEVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.model.records.RecordType;

class AchievementsMapTest {

    @Test
    public void currAchievementsMapIsSameAs_testSameMap() {
        Map<RecordType, List<Achievement>> achievementsMapCopy = new HashMap<>();

        AchievementsMap.ACHIEVEMENTS_MAP.forEach((recordType, achievementsList) -> {
            List<Achievement> achievementListCopy = new ArrayList<>();
            achievementsList.forEach(achievement -> achievementListCopy.add(achievement.copy()));
            achievementsMapCopy.put(recordType, achievementListCopy);
        });

        assertTrue(AchievementsMap.currAchievementsMapIsSameAs(achievementsMapCopy));
    }

    @Test
    public void currAchievementsMapIsSameAs_testDifferentMap() {
        Map<RecordType, List<Achievement>> achievementsMapCopy = new HashMap<>();

        AchievementsMap.ACHIEVEMENTS_MAP.forEach((recordType, achievementsList) -> {
            List<Achievement> achievementListCopy = new ArrayList<>();
            achievementsList.forEach(achievement -> achievementListCopy.add(achievement.copy()));
            achievementsMapCopy.put(recordType, achievementListCopy);
        });

        assertFalse(achievementsMapCopy.keySet().isEmpty());
        List<Achievement> achievementListCopy = achievementsMapCopy.get(achievementsMapCopy.keySet().iterator().next());
        assertFalse(achievementListCopy.isEmpty());
        if (achievementListCopy.get(0).getAchievementState() == ACHIEVED) {
            achievementListCopy.get(0).setAchievementState(YET_TO_ACHIEVE);
        } else {
            achievementListCopy.get(0).setAchievementState(ACHIEVED);
        }
        assertFalse(AchievementsMap.currAchievementsMapIsSameAs(achievementsMapCopy));
    }

}

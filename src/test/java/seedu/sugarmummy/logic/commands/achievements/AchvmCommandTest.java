package seedu.sugarmummy.logic.commands.achievements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.achievements.AchvmCommand.SHOWING_ACHVM_MESSAGE;
import static seedu.sugarmummy.logic.commands.achievements.AchvmCommand.SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS;
import static seedu.sugarmummy.logic.commands.achievements.AchvmCommand.SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS;
import static seedu.sugarmummy.model.achievements.AchievementsMap.ACHIEVEMENTS_MAP;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ModelStub;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.ui.DisplayPaneType;

class AchvmCommandTest {
    private Model model = new ModelStubForAchievements();
    private final String encouragementMessage = userHasNoAchievements()
            ? SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS
            : SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS;
    private Model expectedModel = new ModelStubForAchievements();

    /**
     * Returns whether or not a user has achievements.
     */
    private boolean userHasNoAchievements() {
        Map<RecordType, List<Achievement>> achievementsMap = model.getAchievementsMap();
        for (RecordType recordType : achievementsMap.keySet()) {
            List<Achievement> achievementList = achievementsMap.get(recordType);
            for (Achievement achievement : achievementList) {
                if (!achievement.isYetToBeAchieved()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void execute_achvm_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_ACHVM_MESSAGE + " "
                + encouragementMessage, false, false,
                true, false);
        assertCommandSuccess(new AchvmCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void getDisplayPaneType_test() {
        assertEquals(DisplayPaneType.ACHVM, new AchvmCommand().getDisplayPaneType());
    }

    @Test
    public void getNewPaneIsToBeCreated_test() {
        assertTrue(new AchvmCommand().isToCreateNewPane());
    }

    private static class ModelStubForAchievements extends ModelStub {
        @Override
        public Map<RecordType, List<Achievement>> getAchievementsMap() {
            return ACHIEVEMENTS_MAP;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof ModelStubForAchievements)) {
                return false;
            } else {
                return getAchievementsMap().equals(((ModelStubForAchievements) obj)
                        .getAchievementsMap());
            }
        }
    }

}

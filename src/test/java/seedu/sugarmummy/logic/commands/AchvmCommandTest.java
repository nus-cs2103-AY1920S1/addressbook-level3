package seedu.sugarmummy.logic.commands;

import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.achvm.AchvmCommand.SHOWING_ACHVM_MESSAGE;
import static seedu.sugarmummy.logic.commands.achvm.AchvmCommand.SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS;
import static seedu.sugarmummy.logic.commands.achvm.AchvmCommand.SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.achvm.AchvmCommand;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ModelManager;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.record.RecordType;

class AchvmCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    private final String encouragementMessage = userHasNoAchievements()
            ? SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS
            : SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS;

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
}

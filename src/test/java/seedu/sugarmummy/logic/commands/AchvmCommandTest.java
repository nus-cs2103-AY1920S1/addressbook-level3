package seedu.sugarmummy.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.achvm.AchvmCommand.SHOWING_ACHVM_MESSAGE;
import static seedu.address.logic.commands.achvm.AchvmCommand.SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS;
import static seedu.address.logic.commands.achvm.AchvmCommand.SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.achvm.AchvmCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.achievements.Achievement;
import seedu.address.model.record.RecordType;
>>>>>>> 31d9457e8708450c49017c703a3210a51bff0c2f:src/test/java/seedu/address/logic/commands/AchvmCommandTest.java

class AchvmCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

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

    private final String ENCOURAGEMENT_MESSAGE = userHasNoAchievements()
            ? SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS
            : SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS;

    @Test
    public void execute_achvm_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_ACHVM_MESSAGE + " "
                + ENCOURAGEMENT_MESSAGE, false, false,
                true, false);
        assertCommandSuccess(new AchvmCommand(), model, expectedCommandResult, expectedModel);
    }
}

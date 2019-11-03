package seedu.sugarmummy.logic.commands.achvm;

import java.util.List;
import java.util.Map;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.record.RecordType;
import seedu.sugarmummy.ui.DisplayPaneType;
/**
 * Displays information on user's biography.
 */
public class AchvmCommand extends Command {

    public static final String COMMAND_WORD = "achvm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays information on user's achievements.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_ACHVM_MESSAGE = "Here're your achievements so far.";
    public static final String SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS = "Keep up the good work and be honest with "
            + "your records as it is your own health you're looking after!";
    public static final String SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS = "Oops! It looks like you have not "
            + "collected any achievements so far. But don't worry, I'm sure you'll be able to collect some soon! :)";

    @Override
    public CommandResult execute(Model model) {
        boolean userHasNoAchievements = true;

        Map<RecordType, List<Achievement>> achievementsMap = model.getAchievementsMap();
        for (RecordType recordType : achievementsMap.keySet()) {
            List<Achievement> achievementList = achievementsMap.get(recordType);
            for (Achievement achievement : achievementList) {
                if (!achievement.isYetToBeAchieved()) {
                    userHasNoAchievements = false;
                    break;
                }
            }
            if (!userHasNoAchievements) {
                break;
            }
        }

        final String encouragementMessage = userHasNoAchievements
                ? SHOWING_ENCOURAGEMENT_WITHOUT_ACHIEVEMENTS
                : SHOWING_ENCOURAGEMENT_WITH_ACHIEVEMENTS;

        return new CommandResult(SHOWING_ACHVM_MESSAGE + " " + encouragementMessage,
                false, false, true, false);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.ACHVM;
    }

    @Override
    public boolean getNewPaneIsToBeCreated() {
        return true;
    }
}

package seedu.address.logic.commands.achvm;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.achievements.Achievement;
import seedu.address.model.record.RecordType;
import seedu.address.ui.DisplayPaneType;

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
            + "collected any achievements so far. But don't worry, I'm sure you'll be able to meet the requirements "
            + "soon. Key in some records, and after meeting the requirements for a record type for 3 "
            + "consecutive days, you'll get your first achievement in no time! Be honest with your records as it is "
            + "your own health you're looking after! :)";

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

package seedu.address.logic.commands.verification;

import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeAll;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeCore;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeFocus;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeMcs;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Checks all graduation requirements.
 */
public class CheckCommand extends Command {

    public static final String COMMAND_WORD = "check";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Checking all graduation requirements";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks all graduation requirements.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        if (activeStudyPlan == null) {
            return new CommandResult("You do not have a study plan!");
        } else {
            String result = describeAll(activeStudyPlan) + "\n"
                    + describeMcs(activeStudyPlan) + "\n"
                    + describeCore(activeStudyPlan) + "\n"
                    + describeFocus(activeStudyPlan);
            return new CommandResult(result);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        return other instanceof CheckCommand;
    }
}

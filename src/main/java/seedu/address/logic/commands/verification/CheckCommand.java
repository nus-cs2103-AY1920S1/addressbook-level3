package seedu.address.logic.commands.verification;

import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeAll;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeCore;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeCoreFulfilled;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeFocus;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeFocusFulfilled;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeMcs;
import static seedu.address.model.studyplan.StudyPlanVerificationHelper.describeMcsFulfilled;

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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks all graduation requirements.\n"
            + "Parameters: mcs OR core OR focus (OR none)\n"
            + "Examples: check mcs, check core, check focus, check";

    private String checkArea;

    public CheckCommand() {
        this("ALL");
    }

    public CheckCommand(String checkArea) {
        this.checkArea = checkArea;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StudyPlan activeStudyPlan = model.getActiveStudyPlan();

        if (activeStudyPlan == null) {
            return new CommandResult("You do not have a study plan!");
        }

        String result;

        if ("MCS".equals(this.checkArea)) {
            result = describeMcsFulfilled(activeStudyPlan) + "\n"
                    + describeMcs(activeStudyPlan);
        } else if ("CORE".equals(this.checkArea)) {
            result = describeCoreFulfilled(activeStudyPlan) + "\n"
                    + describeCore(activeStudyPlan);
        } else if ("FOCUS".equals(this.checkArea)) {
            result = describeFocusFulfilled(activeStudyPlan) + "\n"
                    + describeFocus(activeStudyPlan);
        } else {
            result = describeAll(activeStudyPlan) + "\n\n"
                    + describeMcs(activeStudyPlan) + "\n"
                    + describeCore(activeStudyPlan) + "\n"
                    + describeFocus(activeStudyPlan);
        }
        return new CommandResult(result);
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

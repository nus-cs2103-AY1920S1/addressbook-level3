package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.model.versiontracking.exception.StudyPlanCommitManagerNotFoundException;

/**
 * Represents a command for the user to view all commit history of the current active study plan.
 */
public class ViewCommitHistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Checking commit history of the current study plan";
    public static final String MESSAGE_SUCCESS = "Here is the commit history of the current study plan:\n";
    public static final String MESSAGE_NO_COMMIT_HISTORY = "Your current study plan has no commit history yet!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the commit history of the current study plan.\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        if (activeStudyPlan == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        try {
            int activeStudyPlanIndex = activeStudyPlan.getIndex();
            CommitList commitList = model.getCommitListByStudyPlanIndex(activeStudyPlanIndex);
            String commitHistoryText = commitList.toString();

            return new CommandResult(MESSAGE_SUCCESS + commitHistoryText);
        } catch (StudyPlanCommitManagerNotFoundException e) {
            throw new CommandException(MESSAGE_NO_COMMIT_HISTORY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ViewCommitHistoryCommand;
    }

}

package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.storage.ViewCommitHistoryCommand.MESSAGE_NO_COMMIT_HISTORY;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;
import seedu.address.model.versiontracking.Commit;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.model.versiontracking.exception.StudyPlanCommitManagerNotFoundException;
import seedu.address.ui.ResultViewType;

/**
 * Represents a command for the user to view the version of the current active study plan for a particular commit.
 * This does not discard the commits after the specified commit.
 */
public class ViewCommitCommand extends Command {

    public static final String COMMAND_WORD = "viewcommit";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the study plan commit identified by the index number used in the displayed commit list.\n"
            + "Parameters: PLAN_INDEX.COMMIT_NUMBER (both must be non-negative integers)\n";
    public static final String MESSAGE_SUCCESS = "Here is your study plan for this commit. Please do not modify it.";
    public static final String MESSAGE_NO_SUCH_COMMIT = "The commit index you've entered is invalid!";

    private int studyPlanIndex;
    private int commitNumber;

    public ViewCommitCommand(int studyPlanIndex, int commitNumber) {
        this.studyPlanIndex = studyPlanIndex;
        this.commitNumber = commitNumber;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // TODO: THIS IS NOT IMPLEMENTED YET.

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        if (activeStudyPlan == null) {
            throw new StudyPlanNotFoundException();
        }

        try {
            int activeStudyPlanIndex = activeStudyPlan.getIndex();
            CommitList commitList = model.getCommitListByStudyPlanIndex(activeStudyPlanIndex);
            Commit commit = commitList.getCommitByIndex(commitNumber);
            StudyPlan studyPlan = commit.getStudyPlan();
            ObservableList<Semester> semesters = studyPlan.getSemesters().asUnmodifiableObservableList();

            return new CommandResult<>(MESSAGE_SUCCESS, ResultViewType.STUDY_PLAN, semesters);
        } catch (StudyPlanCommitManagerNotFoundException e) {
            return new CommandResult(MESSAGE_NO_COMMIT_HISTORY);
        }

    }
}

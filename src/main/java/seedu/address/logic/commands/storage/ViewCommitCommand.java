package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_ACTIVE_STUDY_PLAN;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;
import static seedu.address.logic.commands.storage.ViewCommitHistoryCommand.MESSAGE_NO_COMMIT_HISTORY;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.Commit;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.model.versiontracking.exception.StudyPlanCommitManagerNotFoundException;
import seedu.address.ui.ResultViewType;

/**
 * Represents a command for the user to view a simplified version of the current active study plan
 * for a particular commit.
 */
public class ViewCommitCommand extends Command {

    public static final String COMMAND_WORD = "viewcommit";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing a study plan commit";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the study plan commit identified by the index number used in the displayed commit list.\n"
            + "Parameters: PLAN_ID.COMMIT_NUMBER (both must be non-negative integers)\n";
    public static final String MESSAGE_SUCCESS = "Here is a simple view of your study plan for this commit:";
    public static final String MESSAGE_NO_SUCH_COMMIT = "The commit index you've entered is invalid!";

    private int studyPlanIndex;
    private int commitNumber;

    public ViewCommitCommand(int studyPlanIndex, int commitNumber) {
        this.studyPlanIndex = studyPlanIndex;
        this.commitNumber = commitNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        if (activeStudyPlan == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        // if the index is different from active study plan, throw an error
        if (activeStudyPlan.getIndex() != studyPlanIndex) {
            throw new CommandException(MESSAGE_NOT_ACTIVE_STUDY_PLAN);
        }

        try {
            int activeStudyPlanIndex = activeStudyPlan.getIndex();
            CommitList commitList = model.getCommitListByStudyPlanIndex(activeStudyPlanIndex);
            Commit commit = commitList.getCommitByIndex(commitNumber);
            StudyPlan studyPlan = commit.getStudyPlan();
            ObservableList<Semester> semesters = studyPlan.getSemesters().asUnmodifiableObservableList();

            return new CommandResult<>(MESSAGE_SUCCESS, ResultViewType.SEMESTER, semesters);
        } catch (StudyPlanCommitManagerNotFoundException e) {
            throw new CommandException(MESSAGE_NO_COMMIT_HISTORY);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NO_SUCH_COMMIT);
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ViewCommitCommand) {
            return this.commitNumber == ((ViewCommitCommand) obj).commitNumber
                    && this.studyPlanIndex == ((ViewCommitCommand) obj).studyPlanIndex;
        }
        return false;
    }
}

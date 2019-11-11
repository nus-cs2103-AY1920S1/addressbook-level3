package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_ACTIVE_STUDY_PLAN;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.exception.CommitNotFoundException;


/**
 * Deletes a commit identified using it's displayed index in the commit list of the current active study plan.
 */
public class DeleteCommitCommand extends Command {

    public static final String COMMAND_WORD = "removecommit";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Deleting a commit";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the commit identified by the index number used in the displayed commit list.\n"
            + "Parameters: STUDY_PLAN_ID.COMMIT_INDEX (must be non-negative integers)\n"
            + "Example: " + COMMAND_WORD + " 1.3";
    public static final String MESSAGE_SUCCESS = "Deleted commit: %1$s";
    public static final String MESSAGE_INVALID_COMMIT_INDEX = "The commit number you've entered is invalid.";

    private final int studyPlanIndex;
    private final int commitNumber;

    public DeleteCommitCommand(int studyPlanIndex, int commitNumber) {
        this.studyPlanIndex = studyPlanIndex;
        this.commitNumber = commitNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        StudyPlan activeStudyPlan = model.getActiveStudyPlan();

        // if the current active study plan is null
        if (activeStudyPlan == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        // if the index entered is not the current active study plan
        if (studyPlanIndex != activeStudyPlan.getIndex()) {
            throw new CommandException(MESSAGE_NOT_ACTIVE_STUDY_PLAN);
        }

        try {
            model.deleteCommit(studyPlanIndex, commitNumber);
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    studyPlanIndex + "." + commitNumber));
        } catch (CommitNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_COMMIT_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommitCommand // instanceof handles nulls
                && studyPlanIndex == ((DeleteCommitCommand) other).studyPlanIndex
                && commitNumber == ((DeleteCommitCommand) other).commitNumber); // state check
    }
}

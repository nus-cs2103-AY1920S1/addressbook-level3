package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.versiontracking.exception.CommitNotFoundException;


/**
 * Deletes a commit identified using it's displayed index in the commit list of the current active study plan.
 */
public class DeleteCommitCommand extends Command {

    public static final String COMMAND_WORD = "deletecommit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the commit identified by the index number used in the displayed commit list.\n"
            + "Parameters: STUDYPLAN_INDEX.COMMIT_INDEX (must be non-negative integers)\n"
            + "Example: " + COMMAND_WORD + " 1.3";

    public static final String MESSAGE_DELETE_COMMIT_SUCCESS = "Deleted commit: %1$s";
    public static final String MESSAGE_INVALID_COMMIT_INDEX = "The commit number you've entered is invalid.";
    public static final String MESSAGE_INVALID_STUDY_PLAN_INDEX =
            "You only can delete a commit in the active study plan!";


    private final int studyPlanIndex;
    private final int commitNumber;

    public DeleteCommitCommand(int studyPlanIndex, int commitNumber) {
        this.studyPlanIndex = studyPlanIndex;
        this.commitNumber = commitNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if the study plan index does not match the current active plan, don't allow this operation.
        if (studyPlanIndex != model.getActiveStudyPlan().getIndex()) {
            return new CommandResult(MESSAGE_INVALID_STUDY_PLAN_INDEX);
        }

        try {
            model.deleteCommit(studyPlanIndex, commitNumber);
            return new CommandResult(String.format(MESSAGE_DELETE_COMMIT_SUCCESS,
                    studyPlanIndex + "." + commitNumber));
        } catch (CommitNotFoundException e) {
            return new CommandResult(MESSAGE_INVALID_COMMIT_INDEX);
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
